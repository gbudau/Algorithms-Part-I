JAVAC=javac
OBJ=Deque.class RandomizedQueue.class Permutation.class
INNER_CLASSES='Deque$$DequeIterator.class' 'Deque$$Node.class' 'RandomizedQueue$$RandomizedQueueIterator.class'

%.class: %.java
	$(JAVAC) $<

.PHONY: all
all: $(OBJ)

.PHONY: clean
clean:
	rm -f $(OBJ) $(INNER_CLASSES)
