var battle = {

    init: function(ev) {
        a_ev = ev;
    },

    create: function() {

        // INIT MUSIC
        // a_mus = game.add.audio('combat1');
        // a_mus.volume = 0.25;
        // a_mus.play()

        /*
        // Opening transition.
        // TODO
        */
        game.add.tween(game.world).to({
            alpha: 1
        }, 750, Phaser.Easing.Exponential.OutIn, true);

        for (i of[0, 1, 2]) {
            var posx_base = (i * 2 + 1.5) / 9 * 960;
            a_gui[i][0] = game.add.sprite(posx_base + 960 * 2 / 117, 560 / 32, 'charSSC');
            initSprite(a_gui[i][0], 0, [0.5, 0.25], 2, 1);
            a_gui[i][3] = initRect(8 / 117 * 960, 540 / 48, posx_base + 960 * 2 / 39, 540 / 32, "#9E9E9E");
            initSprite(a_gui[i][3], 0, [0, 0.5], 1, 1);
            a_gui[i][4] = initRect(8 / 117 * 960, 540 / 48, posx_base + 960 * 16 / 117, 540 / 32, "#9E9E9E");
            initSprite(a_gui[i][4], 0, [0, 0.5], 1, 1);
            a_gui[i][1] = initRect(8 / 117 * 960, 540 / 48, posx_base + 960 * 2 / 39, 540 / 32, "#EF5350");
            initSprite(a_gui[i][1], 0, [0, 0.5], 1, 1);
            a_gui[i][2] = initRect(8 / 117 * 960, 540 / 48, posx_base + 960 * 16 / 117, 540 / 32, "#5C6BC0");
            initSprite(a_gui[i][2], 0, [0, 0.5], 1, 1);
            a_gui[i][5] = game.add.bitmapText(posx_base + 960 * 10 / 117, 540 / 32 + 1, 'Munro', "16", 16);
            initSprite(a_gui[i][5], 0, [0.5, 0.5], 1, 1);
            a_gui[i][6] = game.add.bitmapText(posx_base + 960 * 20 / 117, 540 / 32 + 1, 'Munro', "64", 16);
            initSprite(a_gui[i][6], 0, [0.5, 0.5], 1, 1);
        }

        /*
        // Init bg.
        */
        a_pause = game.add.sprite(0, 0, 'pause');
        initSprite(a_pause, 0, [0, 0], 4, 1);
        a_pause.visible = false;

        a_bg = game.add.tileSprite(0, 560 / 16, 0, 102, 'bg_nightPlain');
        initSprite(a_bg, 0, [0, 0], 4, 1);
        initInput(a_bg, function() {
            a_paused ^= 1;
            a_pause.visible ^= 1;
        });

        a_ev.man.initChars();

        a_text1 = game.add.bitmapText(28, 560 * 14 / 16 - 4, "Munro", "", 24);
        initSprite(a_text1, 0, [0, 1], 1, 1);
        a_text2 = game.add.bitmapText(28, 560 * 15 / 16 - 4, "Munro", "", 24);
        initSprite(a_text2, 0, [0, 1], 1, 1);

        a_pause.bringToTop();

        a_ev.man.doTurn();

    },

    update: function() {
        if (!a_paused) {
            if (a_battleTurn.length && a_battleTurn[0][0] == a_frame) {
                if (a_battleTurn[0].length - 1) {
                    switch (a_battleTurn[0][1]) {
                        case "a_ev.man.warms":
                        case "a_ev.man.defense":
                        case "a_ev.man.shell":
                        case "a_ev.man.watch":
                        case "a_ev.man.charge":
                            eval(a_battleTurn[0][1] + "(" + a_battleTurn[0][2] + ");");
                            a_battleTurn = a_battleTurn.slice(1);
                            break;
                        case "a_spin":
                        case "a_move":
                        case "a_ev.man.protect":
                            eval(a_battleTurn[0][1] + "([" + a_battleTurn[0][2][0] + ", " + a_battleTurn[0][2][1] + "]);");
                            a_battleTurn = a_battleTurn.slice(1);
                            break;
                        case "a_ev.man.attack":
                        case "a_ev.man.spell":
                            eval(a_battleTurn[0][1] + "([" + a_battleTurn[0][2][0] + ", " + a_battleTurn[0][2][1] + ", " + a_battleTurn[0][2][2] + "]);");
                            a_battleTurn = a_battleTurn.slice(1);
                            break;
                        case "a_text":
                            for (t of a_battleTurn[0][2]) {
                                a_toDisp.push(t);
                            }
                            a_battleTurn = a_battleTurn.slice(1);
                            break;
                        case "aim":
                            a_ev.man.chars[a_battleTurn[0][2][0]].aim = a_battleTurn[0][2][1];
                            a_battleTurn = a_battleTurn.slice(1);
                            break;
                    }
                } else {
                    a_ev.man.leftIA = a_ev.man.leftIA.slice(1);
                    doAction(a_ev.man);
                    a_frame = -1;
                    a_battleTurn = a_battleTurn.slice(1);
                }
            }

            a_updateText();
            a_updateMove();
            a_updateSpin();
            a_updateFlash();
            a_updateFlying();

            if (a_draw) {

                a_placeChars();
                a_draw = false;
            }

            a_frame++;

        }
    }

};

/*
// Display function.
// a_alpha is the camera angle.
*/
function a_placeChars() {
    /*
    // Get camera position on the unit circle.
    */
    var a_camX = Math.cos(a_alpha);
    var a_camY = Math.sin(a_alpha);

    for (var i = 0; i < a_j.length; i++) {
        var a_d = Math.sqrt(Math.pow(a_ev.man.chars[i].y - a_camY, 2) + Math.pow(a_ev.man.chars[i].x - a_camX, 2));
        var a_theta1 = angle([a_camX, a_camY], [0, 0]);
        var a_theta2 = angle([a_camX, a_camY], [a_ev.man.chars[i].x, a_ev.man.chars[i].y]);
        var a_thetaFinal = a_theta2 - a_theta1;

        /*
        // Process final positions.
        */
        a_ev.man.chars[i].sprite.x = 96 + Math.cos(a_thetaFinal) * a_d * 960 / 2.5;
        if (a_ev.man.chars[i].aim >= 0) {
            a_ev.man.chars[i].sprite.scale.x = -a_ev.man.chars[a_ev.man.chars[i].aim].sprite.scale.x;
        } else {
            a_ev.man.chars[i].sprite.scale.x = a_ev.man.chars[i].sprite.x >= 960 / 2 ? 4 : -4;
        }
        a_ev.man.chars[i].sprite.y = 540 * 23 / 32 - Math.sin(a_thetaFinal) * a_d * 540 / 8;

        var factor = 0.4 / 150 * (a_ev.man.chars[i].sprite.y) + 12 / 150;
        a_ev.man.chars[i].sprite.scale.x = 4 * a_ev.man.chars[i].sprite.scale.x / Math.abs(a_ev.man.chars[i].sprite.scale.x) * factor;
        a_ev.man.chars[i].sprite.scale.y = 4 * factor;

    }
    /*
    // Sort chars (closest in front).
    */
    a_ev.man.cGroup.sort('y', Phaser.Group.SORT_ASCENDING);
}

/*
// Change camera angle with a cool transition.
*/
function a_spin(a) {
    var b2 = a_alpha;
    var c2 = a[0] - a_alpha;
    var tbis2 = 0;
    /*
    // Actually changle the angle using InOut Cubic Easing.
    */
    for (var i = 1; i < a[1] + 1; i++) {
        a_toSpin.push(inOutCubic(i, a[1], b2, c2))
    }
}

function a_updateSpin() {
    if (a_toSpin.length) {
        a_bg.tilePosition.x += (a_toSpin[0] - a_alpha) * a_bgFactor;
        a_alpha = a_toSpin[0];
        a_toSpin = a_toSpin.slice(1);
        a_draw = true;
    }
}

/*
// Move a char.
*/
function a_move(a) {
    var startx = a_ev.man.chars[a[0]].x;
    var starty = a_ev.man.chars[a[0]].y;
    var finalx = a_ev.man.chars[a[1]].x;
    var finaly = a_ev.man.chars[a[1]].y > 0 ? a_ev.man.chars[a[1]].y - 0.2 : a_ev.man.chars[a[1]].y + 0.2;

    a_moveChar(a[0], startx, starty, finalx, finaly, 25, 0);
    a_moveChar(a[0], finalx, finaly, finalx, finaly, 20, 25);
    a_moveChar(a[0], finalx, finaly, startx, starty, 25, 45);

}

function a_moveChar(nb, xi, yi, xf, yf, time, from) {
    for (var i = 1; i < time + 1; i++) {
        addFuse(a_toMove, [
            [nb, "x", xi + (xf - xi) * i / time],
            [nb, "y", yi + (yf - yi) * i / time]
        ], from + i);
    }
}

function a_updateMove() {
    if (a_toMove.length) {
        for (item of a_toMove[0]) {
            switch (item[1]) {
                case "x":
                    a_ev.man.chars[item[0]].x = item[2];
                    break;
                case "y":
                    a_ev.man.chars[item[0]].y = item[2];
                    break;
            }
        }
        a_toMove = a_toMove.slice(1);
        a_draw = true;
    }
}

/*
// Update GUI.
*/
function a_updateGui(man) {
    for (i of[0, 1, 2]) {
        var posx_base = (i * 2 + 1.5) / 9 * 960;
        a_gui[i][1].width = 8 / 117 * 960 * man.chars[i].battleStats[0] / man.chars[i].stats[0];
        a_gui[i][2].width = 8 / 117 * 960 * man.chars[i].battleStats[1] / man.chars[i].stats[1];
        a_gui[i][5].text = man.chars[i].battleStats[0];
        a_gui[i][6].text = man.chars[i].battleStats[1];
    }
}

function a_updateText() {
    if (a_toDisp.length)
        switch (a_toDisp[0]) {
            case "/":
                a_text1.text = a_text2.text;
                a_text2.text = "";
                a_toDisp = a_toDisp.slice(1);
                break;
            case "":
                a_toDisp = a_toDisp.slice(1);
                break;
            default:
                a_text2.text += a_toDisp[0].substr(0, 1);
                a_toDisp[0] = a_toDisp[0].substr(1);
                break;
        }
}

/*
// Flash animations.
*/

function a_flash(chr, nb, int) {
    for (var i = 0; i < nb * int * 2; i++) {
        addFuse(a_toFlash, (i % int == 0) ? [chr] : []);
    }
}

function a_updateFlash() {
    if (a_toFlash.length) {
        if (a_toFlash[0].length)
            a_ev.man.chars[a_toFlash[0]].sprite.visible ^= 1;
        a_toFlash = a_toFlash.slice(1);
    }
}

/*
// Flying damage.
*/

function a_addFlying(nb, to) {
    a_flying.push([game.add.bitmapText(a_ev.man.chars[to].sprite.x, a_ev.man.chars[to].sprite.y - 75, "Munro", String(nb), 36), to, 0]);
    initSprite(a_flying[a_flying.length - 1][0], 0, [0.5, 0.5], 1, 1);
}

function a_updateFlying() {
    for (var i = 0; i < a_flying.length; i++) {
        var hTemp = a_ev.man.chars[a_flying[i][1]].sprite.height;
        var yTemp = a_ev.man.chars[a_flying[i][1]].sprite.y;
        a_flying[i][0].x = a_ev.man.chars[a_flying[i][1]].sprite.x;
        if (a_flying[i][2] < 50) {
            a_flying[i][0].alpha = 1 - Math.exp(-a_flying[i][2] / 10);
            if (a_flying[i][0].alpha > 0.95) {
                a_flying[i][0].alpha = 1;
            }
            a_flying[i][0].y = inOutCubic(a_flying[i][2], 50,
                yTemp - hTemp * 0.75,
                yTemp - hTemp * 1.10 - (yTemp - hTemp * 0.75))
            a_flying[i][2]++;
        } else if (a_flying[i][2] < 60) {
            a_flying[i][0].y = inOutCubic(50, 50,
                yTemp - hTemp * 0.75,
                yTemp - hTemp * 1.10 - (yTemp - hTemp * 0.75))
            if (a_flying[i][0].alpha - 0.05 >= 0) a_flying[i][0].alpha -= 0.05;
            a_flying[i][2]++;
        } else if (a_flying[i][2] < 85) {
            a_flying[i][0].y = inOutCubic(50, 50,
                yTemp - hTemp * 0.75,
                yTemp - hTemp * 1.10 - (yTemp - hTemp * 0.75))
            a_flying[i][2]++;
            if (a_flying[i][0].alpha - 0.05 >= 0)
                a_flying[i][0].alpha -= 0.05;
        } else {
            a_flying[i][0].destroy();
            a_flying = a_flying.splice(1, i);
        }
    }
}
