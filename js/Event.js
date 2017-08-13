/**
 * Generated On: 2016-4-22
 * Class: Event
 */

function Event(chars) {
    //Constructor

    this.man = new Manager(chars);
    this.rewards = [];
    this.round = 0;
    this.state = 0;

}


/**
 * @return {null}
 */
Event.prototype.process = function() {

    game.state.start("battle", true, false, this);

};
