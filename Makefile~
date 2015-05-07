JFLAGS = -g
JC = javac
JARFILE = junit-4.8.1.jar
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	BinaryTreeNode.java \
	SimpleBST.java \
	SimpleTreeWriter.java \
	SimpleTreeWriterImpl.java \
	TreeUtils.java \
	UserTreeInput.java \
	UserTreeInputTest.java

default: classes

classes: $(CLASSES:.java=.class)

test:
	java -classpath $(JUNITPATH) org.junit.runner.JUnitCore $(JAVASRC)

clean:
	$(RM) *.class
