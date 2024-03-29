# Bowling Game Kata in Clojure

![Kata](kata.png)

![jesus](jesus.png)

This Kata was run at the Paris Clojure User Group along with a presentation about [Data Literals](https://speakerdeck.com/jgrodziski/why-data-literals-matters).

## Bowling Game Domain and Rules

![Game](game.png)

The game consists of **10 frames** as shown above. In each frame the player has
two opportunities to knock down 10 pins. The score for the frame is the total
number of pins knocked down, plus bonuses for strikes and spares.

A **spare** is when the player knocks down all 10 pins in two tries. The **bonus** for that frame is the number of pins knocked down by the next roll. ‘/’ represents a spare in the score sheet.
A **strike** is when the player knocks down all 10 pins on his first try. The **bonus** for that frame is the value of the next two balls rolled. ‘X’ represents a strike in the score sheet.

In the **tenth frame** a player who rolls a spare or strike is allowed to roll the **extra balls** to complete the frame (so 3 balls can be rolled in tenth frame).

## Tests

The project uses [Midje](https://github.com/marick/Midje/).

`lein midje` will run all tests.

`lein midje namespace.*` will run only tests beginning with "namespace.".

`lein midje :autotest` will run all the tests indefinitely. It sets up a
watcher on the code files. If they change, only the relevant tests will be
run again.

## Solutions to the Kata

There is my own solution in [src/bowling.core.clj](https://github.com/jgrodziski/clojure-bowling-game/blob/master/src/bowling/core.clj), the one from [FlyingTof](https://twitter.com/devatsky) is in [this repo](https://github.com/flyingtof/clojing) and the one from [Jon](https://twitter.com/ahoy_jon) and [Thomas](https://twitter.com/dikalikatao): [thomas-and-john-solution](https://github.com/jgrodziski/clojure-bowling-game/blob/master/thomas-and-john-solutions/src/bowling-game/core.clj).
