JAVAC=javac
OBJ=HelloWorld.class HelloGoodbye.class RandomWord.class

%.class: %.java
	$(JAVAC) $<

.PHONY: all
all: $(OBJ)

.PHONY: clean
clean:
	rm $(OBJ)
