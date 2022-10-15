# AVRO maven compiler does not generate classes from linked folder

## Used environment:
- java 17
- maven 3.8.6
- OS: ubuntu

## How to reproduce
- run: *mvn clean install* in *project* folder
- actual: compilation passed
- convert schema folder *myschema* to linked folder using *1.prepare-folders-to-issue-reproduction.sh*
- run: *mvn clean install* in *project* folder
- expected: compilation passed
- actual: compilation failed as no AVRO file has been generated
- optional: use *2.revert-changes.sh* to rollback changes and see again working compilation
