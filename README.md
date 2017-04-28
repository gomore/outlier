# outlier

A CLI for detecting outliers in text files. The utility loads a
reference file and a target file of text strings. The frequencies are
compared and the string of the target file are presented back ordered
according to how over-represented they are compared to the
reference-file. The result can be reversed using `tac` or `sed` to find
strings that are under-represented:

     outlier foo bar | sed '1!G;h;$!d'

## Building

Requires lein to build. To build an executable binary called `outlier`
run

    lein bin 
    
This will also copy the binary to `~/bin`.

## Usage

     outlier reference-file.txt target-file.txt

The output format is documented in `lein --help`.

## TODO

- Read the lines from a stream using lazy-seq, reducers, or
  core.async. Just avoid loading everything into mem!
- Print result lazily and terminate if stream closes
- Add the possibility of reading target file from standard in.

## License

Copyright © 2017 GoMore

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
