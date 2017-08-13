/**
 * Generated On: 2016-4-22
 * Class: Perso
 */

function Perso() {
    //Constructor

    this.class = 0;
    this.level = 1;
    this.exp = 0;
    this.runes = [];
    this.baseStats = [50, 100, 5, 5, 5, 5, 5];
    this.equip = [];
    this.baseWeapon = 10;
    this.buffs = [];
    this.stats = [0, 0, 0, 0, 0, 0, 0];
    this.battleStats = [0, 0, 0, 0, 0, 0, 0];
    this.dispSprite = "chars";
    this.sprite = null;
    this.x = 0;
    this.y = 0;
    this.aim = -1;
    this.alive = true;
    this.idle = 0;

}


/**
 * @param amount {int}
 * @return {null}
 */
Perso.prototype.addExp = function(amount) {
    //TODO: Implement Me

};


/**
 * @return {null}
 */
Perso.prototype.updateStats = function() {
    for (var i = 2; i < this.stats.length; i++) {
        this.battleStats[i] = this.stats[i];
    }
    for (var i = 0; i < this.buffs.length; i++) {
        this.battleStats[this.buffs[i][0]] += this.buffs[i][1];
        this.buffs[i][2]--;
        if (this.buffs[i][2] <= 0) {
            this.buffs.splice(i, 1);
        }
    }
};

/**
 * @return {null}
 */
Perso.prototype.calcStats = function() {
    for (var i = 0; i < this.stats.length; i++) {
        for (var j = 0; j < this.equip.length; j++) {
            // TODO : Equip
        }
        this.stats[i] = this.baseStats[i];
    }
};

/**
 * @param chars {Perso Array}
 * @param nb {int}
 * @param turn {int}
 * @return {int Array}
 */
Perso.prototype.getIA = function(chars, nb, turn) {

    if (this.alive && this.idle == 0) {
        //TODO: Implement proper IA
        var c1 = 0;
        do {
            c1 = Math.floor(Math.random() * 3) + 3 - 3 * Math.floor(nb / 3);
        } while (!chars[c1].alive);
        var c2 = 0;
        do {
            c2 = Math.floor(Math.random() * 3) + 3 * Math.floor(nb / 3);
        } while (c2 == nb);
        switch (Math.floor(Math.random() * 11)) {
            case 0: // Swing
                return [nb, 0, c1];
            case 1: // Strike
                return [nb, 1, c1];
            case 2: // Charge
                return [nb, 2];
            case 3: // Read Spell
                return [nb, 3, c1];
            case 4: // Sing Spell
                return [nb, 4, c1];
            case 5: // Defense
                return [nb, 5];
            case 6: // Shell
                return [nb, 6];
            case 7: // Watch Out
                return [nb, 7];
            case 8: // Protect
                return [nb, 8, c2];
            case 9: // Warm Up
                return [nb, 9];
            default:
                return [nb, 10];
        }
    } else if (this.idle > 0) {
        this.idle--;
    }

};

Perso.prototype.damage = function(dmg) {
    if (this.battleStats[0] - dmg > 0) {
        this.battleStats[0] -= dmg;
    } else {
        this.battleStats[0] = 0;
        this.alive = false;
        game.add.tween(this.sprite).to({
            alpha: 0
        }, 750, Phaser.Easing.Exponential.OutIn, true);
    }
};

Perso.prototype.switchVisible = function() {
    this.sprite.visible = !this.sprite.visible;
};

Perso.prototype.setVisible = function() {
    this.sprite.visible = true;
};

Perso.prototype.checkMana = function(nb) {
    if (this.battleStats[1] - nb >= 0) {
        this.battleStats[1] -= nb;
        return true;
    } else {
        return false;
    }
};
