# Simple Dictionary

### Features

1. HTML Based UI
2. English to english dictionary lookup
3. Spell correction (Similar word suggestions)

### Technologies used

1. Simple html UI
2. Java backend (Spring boot webflux)
3. Async communication to backend through simple js

### Algorithms

1. Edit distance
    - Generate all words at distance 1/2 through brute force

### Data

1. It uses open source Wordset dictionary data which was bootstrapped from Princeton WordNet project data.
2. Language model data taken directly from https://norvig.com/spell-correct.html
3. Dictionary data and language model data needs to be downloaded separately to run this

### Running

1. Download the open source dictionary data from [WordSet dictionary](https://github.com/wordset/wordset-dictionary/tree/master/data) 
and put it in ```src/main/resourcses/data/dict/```
2. Download the Language model data from [here](https://norvig.com/big.txt) 
and put it in ```src/main/resourcses/data/model/```

### References

1. [Wordset Dictionary](https://github.com/wordset/wordset-dictionary)
2. [How to Write a Spelling Corrector](https://norvig.com/spell-correct.html)
