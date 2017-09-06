/**
 * Generated On: 2016-4-22
 * Class: Manager
 */

function Manager(chars) {
    //Constructor

    this.chars = chars;
    this.cGroup = null;
    this.turn = 0;
    this.maxTurns = 100;
    this.seed = Math.floor(Math.random() * 9999999);
    this.turnIA = null;

}


/**
 * @return {null}
 */
Manager.prototype.doTurn = function() {

    /*
    // Update stats
    */

    this.chars[0].updateStats();
    this.chars[1].updateStats();
    this.chars[2].updateStats();
    this.chars[3].updateStats();
    this.chars[4].updateStats();
    this.chars[5].updateStats();
    a_updateGui(this);

    /*
    // Sort by speed
    */

    var charTable = [];
    for (var i = 0; i < this.chars.length; i++) {
        charTable.push([i, this.chars[i].battleStats[5]]);
    }
    charTable = triSelection(charTable, 1);

    if (this.turn == 0)
        a_alpha = a_cam[charTable[0][0]];

    /*
    // Get IA
    */

    var turnIA = [];

    for (var i = 0; i < charTable.length; i++) {
        var t = this.chars[charTable[i][0]].getIA(this.chars, charTable[i][0], this.turn);
        if (t)
            turnIA.push(t);
    }

    /*
    // Battle !
    */
    this.leftIA = turnIA;
    doAction(this);
    this.turn++;

};

Manager.prototype.onEnd = function() {
    var state = this.checkWin();
    if (state[0]) {
        var text = state[1] == 1 ? "Vous avez gagné !" : "Vous avez perdu !";
        a_toDisp.push("/");
        a_toDisp.push(text);
    } else {
        this.doTurn();
    }
};

/**
 * @return {null}
 */
Manager.prototype.getOutput = function() {
    //TODO: Implement Me

};

/**
 * @return {null}
 */
Manager.prototype.initChars = function() {
    /*
    // Set correct positions.
    */
    this.chars[0].x = -0.5;
    this.chars[3].x = -0.5;
    this.chars[1].x = 0;
    this.chars[4].x = 0;
    this.chars[2].x = 0.5;
    this.chars[5].x = 0.5;
    this.chars[0].y = -0.5;
    this.chars[1].y = -0.5;
    this.chars[2].y = -0.5;
    this.chars[3].y = 0.5;
    this.chars[4].y = 0.5;
    this.chars[5].y = 0.5;

    /*
    // Add chars.
    */
    this.cGroup = game.add.group();
    this.chars[2].sprite = this.cGroup.create(this.chars[2].x, this.chars[2].y, 'charSSC');
    this.chars[1].sprite = this.cGroup.create(this.chars[1].x, this.chars[1].y, 'charSSC');
    this.chars[0].sprite = this.cGroup.create(this.chars[0].x, this.chars[0].y, 'charSSC');
    initSprite(this.chars[0].sprite, 0, [0.5, 1], 4, 1);
    initSprite(this.chars[1].sprite, 0, [0.5, 1], 4, 1);
    initSprite(this.chars[2].sprite, 0, [0.5, 1], 4, 1);
    this.chars[5].sprite = this.cGroup.create(this.chars[5].x, this.chars[5].y, 'charSSC');
    this.chars[4].sprite = this.cGroup.create(this.chars[4].x, this.chars[4].y, 'charSSC');
    this.chars[3].sprite = this.cGroup.create(this.chars[3].x, this.chars[3].y, 'charSSC');
    initSprite(this.chars[3].sprite, 0, [0.5, 1], 4, 1);
    initSprite(this.chars[4].sprite, 0, [0.5, 1], 4, 1);
    initSprite(this.chars[5].sprite, 0, [0.5, 1], 4, 1);
    this.chars[3].sprite.scale.x = -4;
    this.chars[4].sprite.scale.x = -4;
    this.chars[5].sprite.scale.x = -4;

    this.chars[0].calcStats();
    this.chars[1].calcStats();
    this.chars[2].calcStats();
    this.chars[3].calcStats();
    this.chars[4].calcStats();
    this.chars[5].calcStats();

    this.chars[0].battleStats[0] = this.chars[0].stats[0];
    this.chars[1].battleStats[0] = this.chars[1].stats[0];
    this.chars[2].battleStats[0] = this.chars[2].stats[0];
    this.chars[3].battleStats[0] = this.chars[3].stats[0];
    this.chars[4].battleStats[0] = this.chars[4].stats[0];
    this.chars[5].battleStats[0] = this.chars[5].stats[0];
    this.chars[0].battleStats[1] = this.chars[0].stats[1];
    this.chars[1].battleStats[1] = this.chars[1].stats[1];
    this.chars[2].battleStats[1] = this.chars[2].stats[1];
    this.chars[3].battleStats[1] = this.chars[3].stats[1];
    this.chars[4].battleStats[1] = this.chars[4].stats[1];
    this.chars[5].battleStats[1] = this.chars[5].stats[1];
};

Manager.prototype.attack = function(a) {
    /*
    // Dodge.
    */
    if (Math.floor(Math.random() * 100) < this.chars[a[0]].battleStats[6]) {
        a_toDisp.push("/");
        a_toDisp.push(a[0] + " esquive l'attaque !");
        var dec = this.chars[a[1]].y > 0 ? 0.25 : -0.25;
        var xi = this.chars[a[1]].x;
        var yi = this.chars[a[1]].y;
        a_moveChar(a[1], xi, yi, xi, yi + dec, 10, 0);
        a_moveChar(a[1], xi, yi + dec, xi, yi + dec, 5, 10);
        a_moveChar(a[1], xi, yi + dec, xi, yi, 10, 15);
    } else {
        /*
        // Damage.
        */
        var dmg = 0;
        switch (a[2]) {
            case 0:
                dmg = Math.ceil(this.chars[a[0]].baseWeapon * (0.85 + (this.chars[a[0]].battleStats[2] - this.chars[a[1]].battleStats[3]) / 100));
                this.chars[a[1]].damage(dmg);
                break;
            case 1:
                dmg = Math.ceil(this.chars[a[0]].baseWeapon * (1.15 + (this.chars[a[0]].battleStats[2] - this.chars[a[1]].battleStats[3]) / 100));
                this.chars[a[1]].damage(dmg);
                break;
        }
        /*
        // Flash animation.
        */
        a_flash(a[1], 2, 5);
        a_updateGui(this);

        /*
        // Damage animation.
        */
        a_addFlying(dmg, a[1]);
    }
};

Manager.prototype.spell = function(a) {
    /*
    // Damage.
    */
    var cost = 5;
    if (this.chars[a[0]].checkMana(cost)) {
        switch (a[2]) {
            case 0:
                var dmg = Math.ceil(this.chars[a[0]].baseWeapon * (0.85 + (this.chars[a[0]].battleStats[2] - this.chars[a[1]].battleStats[3]) / 100));
                this.chars[a[1]].damage(dmg);
                break;
            case 1:
                var dmg = Math.ceil(this.chars[a[0]].baseWeapon * (1.15 + (this.chars[a[0]].battleStats[2] - this.chars[a[1]].battleStats[3]) / 100));
                this.chars[a[1]].damage(dmg);
                break;
        }
        /*
        // Flash animation.
        */
        a_flash(a[1], 2, 5);
        a_addFlying(dmg, a[1]);
    } else {
        a_toDisp.push("/");
        a_toDisp.push("Pas assez de mana !");
    }
    a_updateGui(this);
};

Manager.prototype.defense = function(nb) {
    this.chars[nb].battleStats[3] += 15;
    a_flash(nb, 2, 10);
};

Manager.prototype.shell = function(nb) {
    this.chars[nb].battleStats[3] += 35;
    this.chars[nb].buffs.push([3, 35, 1]);
    this.chars[nb].idle = 1;
    a_flash(nb, 2, 5);
};

Manager.prototype.warms = function(nb) {
    this.chars[nb].battleStats[3] += 5;
    this.chars[nb].buffs.push([2, 15, 1]);
    a_flash(nb, 2, 5);
};

Manager.prototype.charge = function(nb) {
    this.chars[nb].battleStats[3] += 5;
    this.chars[nb].buffs.push([2, 15, 1]);
    a_flash(nb, 2, 5);
};

Manager.prototype.watch = function(nb) {
    this.chars[nb].battleStats[3] += 5;
    this.chars[nb].buffs.push([2, 15, 1]);
    a_flash(nb, 2, 5);
};


Manager.prototype.watches = function(nb) {
    this.chars[nb].battleStats[6] += 35;
    a_flash(nb, 2, 5);
};

Manager.prototype.protect = function(t) {
    // TODO
};

Manager.prototype.checkWin = function() {
    if (this.chars[0].alive == false && this.chars[1].alive == false && this.chars[2].alive == false) {
        return [true, 0];
    } else if (this.chars[3].alive == false && this.chars[4].alive == false && this.chars[5].alive == false) {
        return [true, 1];
    } else {
        return [false];
    }
};
