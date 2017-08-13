/*
 ** Battle
 */
var a_bg;
var a_view = 0;
var a_j = [null, null, null, null, null, null];
var a_jGroup;
var a_cam = [10.5 * Math.PI / 16, 11 * Math.PI / 16, 11.5 * Math.PI / 16, -4.5 * Math.PI / 16, -5 * Math.PI / 16, -5.5 * Math.PI / 16];
var a_camActive = 0;
var a_alpha = a_cam[0];
var a_block = false;
var a_bgFactor = 75;
var a_transitionInterval = 20;
var a_draw = true;
var a_int = null;
var a_ev = null;
var a_text1 = null;
var a_text2 = null;
var a_gui = [
    [null, null, null, null, null, null, null, null],
    [null, null, null, null, null, null, null, null],
    [null, null, null, null, null, null, null, null]
];
var a_toDisp = [];
var a_toMove = [];
var a_toSpin = [];
var a_toFlash = [];
var a_flying = [];
var a_battleTurn = [];
var a_frame = 0;
var a_funName = ["a_spin", "a_text", "aim", "a_move", "a_ev.man.attack", "a_ev.man.spell", "a_ev.man.warms", "a_ev.man.defense", "a_ev.man.shell", "a_ev.man.watch", "a_ev.man.protect", "a_ev.man.charge"];
var a_paused;
var a_pause = null;
var a_mus = null;

/*
 ** Manager
 */
var m_leftIA = [];

/**
 * @return {null}
 * Called by Manager on doTurn.
 */
function doAction(man) {
    if (man.leftIA.length == 0 || man.checkWin()[0]) {
        man.onEnd();
        return;
    }
    var ia = man.leftIA[0];
    if (!man.chars[ia[0]].alive) {
        man.leftIA = man.leftIA.slice(1);
        doAction(man);
    } else {
        switch (ia[1]) {
            case 0: // Swing
                var framesSpin = Math.ceil(calcSpin(Math.abs(a_cam[ia[0]] - a_alpha)));
                a_battleTurn.push([0, a_funName[0],
                    [a_cam[ia[0]], framesSpin]
                ]);
                a_battleTurn.push([framesSpin - 15, a_funName[1],
                    ["/", ia[0] + " porte un coup rapide à " + ia[2] + " !"]
                ]);
                a_battleTurn.push([framesSpin - 10, a_funName[2],
                    [ia[0], ia[2]]
                ]);
                a_battleTurn.push([framesSpin, a_funName[3],
                    [ia[0], ia[2]]
                ]);
                a_battleTurn.push([framesSpin + 25, a_funName[4],
                    [ia[0], ia[2], 0]
                ]);
                a_battleTurn.push([framesSpin + 70, a_funName[2],
                    [ia[0], -1]
                ]);
                a_battleTurn.push([framesSpin + 75]);
                break;
            case 1: // Strike
                var framesSpin = Math.ceil(calcSpin(Math.abs(a_cam[ia[0]] - a_alpha)));
                a_battleTurn.push([0, a_funName[0],
                    [a_cam[ia[0]], framesSpin]
                ]);
                a_battleTurn.push([framesSpin - 15, a_funName[1],
                    ["/", ia[0] + " porte un coup à " + ia[2] + " !"]
                ]);
                a_battleTurn.push([framesSpin - 10, a_funName[2],
                    [ia[0], ia[2]]
                ]);
                a_battleTurn.push([framesSpin, a_funName[3],
                    [ia[0], ia[2]]
                ]);
                a_battleTurn.push([framesSpin + 25, a_funName[4],
                    [ia[0], ia[2], 1]
                ]);
                a_battleTurn.push([framesSpin + 70, a_funName[2],
                    [ia[0], -1]
                ]);
                a_battleTurn.push([framesSpin + 75]);
                break;
            case 2: // Warm Up
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " se prépare !"]
                ]);
                a_battleTurn.push([15, a_funName[6], ia[0]]);
                a_battleTurn.push([75]);
                break;
            case 3: // Quick spell
                var framesSpin = Math.ceil(calcSpin(Math.abs(a_cam[ia[0]] - a_alpha)));
                a_battleTurn.push([2, a_funName[0],
                    [a_cam[ia[0]], framesSpin]
                ]);
                a_battleTurn.push([framesSpin - 15, a_funName[1],
                    ["/", ia[0] + " lit un sort !"]
                ]);
                a_battleTurn.push([framesSpin, a_funName[5],
                    [ia[0], ia[2], 0]
                ]);
                a_battleTurn.push([framesSpin + 35]);
                break;
            case 4: // Powerful spell
                var framesSpin = Math.ceil(calcSpin(Math.abs(a_cam[ia[0]] - a_alpha)));
                a_battleTurn.push([2, a_funName[0],
                    [a_cam[ia[0]], framesSpin]
                ]);
                a_battleTurn.push([framesSpin - 15, a_funName[1],
                    ["/", ia[0] + " jette un sort !"]
                ]);
                a_battleTurn.push([framesSpin, a_funName[5],
                    [ia[0], ia[2], 1]
                ]);
                a_battleTurn.push([framesSpin + 35]);
                break;
            case 5: // Defense
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " se défend !"]
                ]);
                a_battleTurn.push([15, a_funName[7], ia[0]]);
                a_battleTurn.push([75]);
                break;
            case 6: // Shell
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " se protège !"]
                ]);
                a_battleTurn.push([15, a_funName[8], ia[0]]);
                a_battleTurn.push([75]);
                break;
            case 7: // Watch Out
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " se prépare à esquiver !"]
                ]);
                a_battleTurn.push([15, a_funName[9], ia[0]]);
                a_battleTurn.push([75]);
                break;
            case 8: // Protect
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " protège " + ia[2] + " !"]
                ]);
                a_battleTurn.push([15, a_funName[10],
                    [ia[0], ia[2]]
                ]);
                a_battleTurn.push([75]);
                break;
            case 9: // Charge
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " se chauffe !"]
                ]);
                a_battleTurn.push([15, a_funName[11], ia[0]]);
                a_battleTurn.push([75]);
                break;
            case 10: // Wait
                a_battleTurn.push([5, a_funName[1],
                    ["/", ia[0] + " attend…"]
                ]);
                a_battleTurn.push([75]);
                break;
        }
    }
}

function calcSpin(nb) {
    return 7 * nb * nb + 5 * nb + 30;
}

function addFuse(t1, t2, i) {
    if (t1[i]) {
        t1[i] = t1[i].concat(t2);
    } else {
        while (t1.length < i) {
            t1.push([]);
        }
        t1.push(t2);
    }
}

Array.prototype.swap = function(x, y) {
    var b = this[x];
    this[x] = this[y];
    this[y] = b;
    return this;
}

function triSelection(li, k) {
    var li2 = [];
    for (var i = 0; i < li.length; i++) {
        // On ajoute l'élément au début de la liste
        li2.unshift(li[i]);
        var j = 0;
        // On décale à droite tant que l'élément est trop petit
        while (li2[j + 1] ? li2[j][k] < li2[j + 1][k] : false) {
            li2.swap(j, j + 1);
            j++;
        }
        // On trie par ordre croissant d'ID
        while (li2[j + 1] ? li2[j][k] == li2[j + 1][k] && li2[j][0] < li2[j + 1][0] : false) {
            li2.swap(j, j + 1);
            j++;
        }
    }
    return li2;
}

/*
// Return the angle made by two vectors.
*/
function angle(p0, p1) {
    var dx = p1[0] - p0[0];
    var dy = p1[1] - p0[1];
    return Math.atan2(dy, dx);
}

function inOutCubic(t, tMax, beg, end) {
    tbis = t;
    tbis /= tMax / 2;
    if (tbis < 1) {
        return end / 2 * tbis * tbis * tbis + beg;
    } else {
        tbis -= 2;
        return end / 2 * (tbis * tbis * tbis + 2) + beg;
    }
}
