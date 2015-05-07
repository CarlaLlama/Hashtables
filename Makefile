JFLAGS = -g
JC = javac
JARFILE = junit-4.8.1.jar
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Dictionary.java \
	Definition.java \
	WordType.java \
	Entry.java \
	EntryImpl.java \
	SCEntryImpl.java \
	ChainedEntryImpl.java \
	LPHashtable.java \
	QPHashtable.java \
	SCHashtable.java \
	FileUtil.java \
	UserInterface.java \
	ProbeCounter.java \
	SearchProbeCounter.java


default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
