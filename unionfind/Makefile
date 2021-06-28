JAVAC=javac
OBJ=Client.class UF.class

%.class: %.java
	$(JAVAC) $<

.PHONY: all
all: $(OBJ)

.PHONY: clean
clean:
	rm $(OBJ)
