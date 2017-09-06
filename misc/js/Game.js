// TODO : Fix shitty font

var game = new Phaser.Game(960, 540, Phaser.CANVAS, "game");

game.state.add("boot", bootState);
game.state.add("load", loadState);
game.state.add("battle", battle);

// INIT MUSIC
var ArtRemix;
// ArtRemix = new ChiptuneJsPlayer(new ChiptuneJsConfig(1));

// INIT JOBS
// var JOBS = [null, null, null, null, null, null, null];
// JOBS[0] = new Job("Warrior", 30, 1, 5, 3, 3, 2, 3);
// JOBS[1] = new Job("Priest", 30, 5, 1, 3, 3, 4, 2);
// JOBS[2] = new Job("Mage", 30, 6, 1, 5, 2, 4, 3);
// JOBS[3] = new Job("Paladin", 50, 3, 3, 2, 4, 3, 2);
// JOBS[4] = new Job("Flutist", 10, 5, 1, 3, 5, 5, 4);
// JOBS[5] = new Job("Ninja", 30, 1, 6, 2, 3, 6, 6);
// JOBS[6] = new Job("Gunner", 20, 4, 4, 5, 2, 3, 4);

game.state.start("boot");

function initSprite(sp, smoothed, anchors, scale, alpha) {
    sp.smoothed = (smoothed == 1);
    sp.anchor.set(anchors[0], anchors[1]);
    sp.scale.set(scale);
    sp.alpha = alpha;
}

function initInput(sp, fun) {
    sp.inputEnabled = true;
    sp.input.useHandCursor = true;
    sp.events.onInputDown.add(fun, this);
}

function initRect(w, h, x, y, color) {
    var bmd = game.add.bitmapData(w, h);
    bmd.ctx.beginPath();
    bmd.ctx.rect(0, 0, w, h);
    bmd.ctx.fillStyle = color;
    bmd.ctx.fill();
    return game.add.sprite(x, y, bmd);
}
