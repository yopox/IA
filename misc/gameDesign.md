# Game Design - IA

## TODO

## Principe

Il faut former une équipe de trois personnages en choisissant les éléments suivants :

- Classes des personnages
- Équipements
- Runes de chaque personnage

Les combats sont ensuite automatiques.

## Éléments

Il y a 5 éléments.

Nom | Description
:-: | ------------------------------------------------------------------------------------
HP  | Points de vie
ATK | Force physique
DK  | Noirceur : puissance et résistance aux magies noires
LT  | Lumière : augmente la puissance des soins et des buffs, rend faible à la magie noire
DEF | Defense
SPD | Vitesse

## Runes

Une règle se construit de la façon suivante :

```
PORTE_LOGIQUE CONDITION (VALUE) (TARGET) ACTION (TARGET)
             (CONDITION (VALUE) (TARGET))
```

### Exemples

```
Attaque l'ennemi avec le moins de HP
[ID] [EACH TURN] [ATTACK] [ENEMY WITH LESS HP]

Se défend 2 tours sur 3
[NOT] [EACH 3 TURNS] [DEFEND]

Soigne l'allié mal en point si le soigneur a plus de 50% de ses HP
[AND] [MORE X HP] [50%] [SELF] [SPELL] [ALLY WITH LESS HP]
      [LESS X HP] [20%] [ALLY WITH LESS HP]
```

### Portes logiques

Nom  | Description
:--: | -----------------------------------
 ID  | Évalue la condition
NOT  | Évalue le contraire de la condition
 OR  | OR
XOR  | XOR
NOR  | NOR
AND  | AND
NAND | NAND

### Conditions

Nom  |   Requiert   | Description
:--: | :----------: | ---------------------------------------------
E1T  |      -       | Vérité
EXT  |    VALUE     | Vrai tous les X tours (le premier tour est 1)
ONCE |    VALUE     | Vrai au tour X
T>X  |    VALUE     | Vrai après le tour X
MXHP | VALUE TARGET | Vrai si la cible a plus de X% de HP
LXHP | VALUE TARGET | Vrai si la cible a moins de X% de HP

### Cibles

  Nom    | Description
:------: | ----------------------------------
  SELF   | Soi-même
AM[STAT] | Allié avec le plus de [STAT]
AL[STAT] | Allié avec le moins de [STAT]
EM[STAT] | Ennemi avec le plus de [STAT]
EL[STAT] | Ennemi avec le moins de [STAT]
aM[STAT] | Personnage avec le plus de [STAT]
aL[STAT] | Personnage avec le moins de [STAT]

### Classes de personnages

Nom         | HP  | ATK | LT  | DK | DEF | SPD
----------- | --- | --- | --- | -- | --- | ---
Freelance   | 100 | 20  | 0   | 0  | 5   | 25
Warrior     | 90  | 30  | 5   | 5  | 10  | 20
Rogue       | 75  | 15  | 0   | 10 | 0   | 50
Paladin     | 120 | 18  | 25  | -5 | 15  | 25
Priest      | 85  | 5   | 30  | -5 | 5   | 20
Necromancer | 85  | 10  | -10 | 30 | 0   | 22
