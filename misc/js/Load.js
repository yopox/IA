// LOAD SCREEN
var load_logo, load_titleEmitter;

var loadState = {

    preload: function() {

        // BOOT
        game.load.image("logo", "assets/splash/logo.png");
        game.load.image("part", "assets/splash/bluePart.png");
        game.load.image("part2", "assets/splash/redPart.png");
        game.load.image("phaser", "assets/splash/phaser.png");

        // GUI

        // CHARS
        game.load.image("charSSC", "assets/chars/SS&S.png");
        game.load.spritesheet("chars", "assets/chars/char1.png", 16, 24);

        // ARENA
        game.load.image("bg_nightPlain", "assets/bg/nightPlain.png");
        game.load.image("pause", "assets/bg/pause.png");

        // TITLE

        // CHOOSE
        // game.load.image("arrow", "assets/arrow.png");
        // game.load.spritesheet("ic_rand", "assets/ic_rand_sheet.png", 32, 32);
        // game.load.spritesheet("ic_ok", "assets/ic_ok_sheet.png", 32, 32);
        // game.load.image("ic_sword", "assets/ic_sword.png");
        // game.load.image("ic_shield", "assets/ic_shield.png");
        // game.load.image("ic_boots", "assets/ic_boots.png");

        // MISC


        // FONT
        game.load.bitmapFont('Munro', 'assets/font/mc.png', 'assets/font/mc.fnt');

        // MUSIC
        game.load.audio('combat1', 'assets/music/combat_!.ogg');
        // game.load.audio('audio_degat', 'assets/audio/bruitages/degat.ogg');
        // game.load.audio('audio_explosion', 'assets/audio/bruitages/explosion.ogg');
        // game.load.audio('audio_morsure', 'assets/audio/bruitages/morsure.ogg');
        // game.load.audio('audio_shockwave', 'assets/audio/bruitages/shockwave.ogg');
        // game.load.audio('audio_sprint', 'assets/audio/bruitages/sprint.ogg');
    },

    create: function() {

        var c1 = new Perso(),
            c2 = new Perso(),
            c3 = new Perso(),
            c4 = new Perso(),
            c5 = new Perso(),
            c6 = new Perso();
        for (var i = 1; i < 7; i++) {
            eval("c" + i + ".baseStats[5] = " + Math.ceil(Math.random() * 25) + ";");
        }
        var a_ev = new Event([c1, c2, c3, c4, c5, c6]);

        game.stage.backgroundColor = "#EEE";

        a_ev.process();


        //
        // // PARTICLES
        // load_titleEmitter = game.add.emitter(game.width / 2 + 1, game.height / 2 - 65, 1000);
        // load_titleEmitter.makeParticles(['part', 'part2']);
        // load_titleEmitter.gravity = 0;
        // load_titleEmitter.minParticleSpeed.setTo(-400, -400);
        // load_titleEmitter.maxParticleSpeed.setTo(400, 400);
        //
        // setTimeout(function() {
        //     load_titleEmitter.start(false, 750, 10);
        // }, 950);
        //
        // // ELLECTRON's LOGO
        // load_logo = game.add.sprite(game.width / 2 - 128, game.height / 2 - 192, 'logo');
        // initSprite(load_logo, 0, [0, 0], 4, 0);
        // game.add.tween(load_logo).to({
        //     alpha: 1
        // }, 1000, Phaser.Easing.Exponential.In, true);
        //
        // // FINAL FADE OUT
        // setTimeout(function() {
        //     load_titleEmitter.on = false;
        //     game.add.tween(game.world).to({
        //         alpha: 0
        //     }, 1500, Phaser.Easing.Exponential.OutIn, true);
        // }, 2000);
        //
        // // DESTROY SPRITES AND START TITLE SCREEN
        // setTimeout(function() {
        //     load_logo.destroy();
        // }, 4000);

    },

    update: function() {

    }

};

function binaryLoadCallback(key, data) {

    return new Uint8Array(data);

}
