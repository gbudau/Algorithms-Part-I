JAVAC=javac
OBJ=Percolation.class PercolationStats.class PercolationVisualizer.class

%.class: %.java
	$(JAVAC) $<

.PHONY: all
all: $(OBJ)

.PHONY: clean
clean:
	rm $(OBJ)
