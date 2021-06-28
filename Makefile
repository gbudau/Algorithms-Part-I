JAVAC=javac -g
OBJ=Percolation.class PercolationStats.class

%.class: %.java
	$(JAVAC) $<

.PHONY: all
all: $(OBJ)

.PHONY: clean
clean:
	rm $(OBJ)
