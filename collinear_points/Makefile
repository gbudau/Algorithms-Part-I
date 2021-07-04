JAVAC=javac
OBJ=Point.class LineSegment.class BruteCollinearPoints.class FastCollinearPoints.class

%.class: %.java
	$(JAVAC) $<

.PHONY: all
all: $(OBJ)

.PHONY: clean
clean:
	rm $(OBJ)
