# Makefile du projet
JAVA=$$(find src/ -iname *.java)
SOURCEPATH=src
DOCPATH=doc
CLASSESPATH=classes
NOMMAGEPATH=rmiregistry
SERVERCLASSESPATH=Server/classes
CLIENTCLASSESPATH=Client/classes
CLASSPATH=lib:$(CLASSESPATH)

DOTCLASS=$$(find classes Server Client rmiregistry -iname *.class)


all : compile compileClient compileServer cpNommage

compile :
	javac -sourcepath $(SOURCEPATH) -d $(CLASSESPATH) -cp ./lib/ $(JAVA)
	

compileClient : cpClient
	javac -sourcepath $(SOURCEPATH) -d Client/classes/ -cp $(CLIENTCLASSESPATH) Client/Client.java

compileServer :	cpServer
	javac -sourcepath $(SOURCEPATH) -d Server/classes/ -cp $(SERVERCLASSESPATH) Server/Server.java

cpClient : compile
	cp -R --parents ./$(CLASSESPATH)/bonbombe/controllers/impl/ClientController* Client/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/controllers/interf/* Client/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/enums/* Client/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/models/interf/* Client/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/views/* Client/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/exceptions/* Client/

cpServer : compile
	cp -R --parents ./$(CLASSESPATH)/bonbombe/controllers/* Server/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/enums/* Server/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/models/* Server/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/views/* Server/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/exceptions/* Server/
 	
cpNommage : compile
	cp -R --parents ./$(CLASSESPATH)/bonbombe/controllers/interf/* rmiregistry/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/enums/* rmiregistry/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/models/interf/* rmiregistry/
	cp -R --parents ./$(CLASSESPATH)/bonbombe/exceptions/* rmiregistry/
	mv $(NOMMAGEPATH)/classes/* $(NOMMAGEPATH)
	rm -rf $(NOMMAGEPATH)/classes

documentation :
	javadoc -sourcepath $(SOURCEPATH) \
			-d $(DOCPATH) \
			-windowtitle "Bonbombe : Documentation" \
			-doctitle "BonBombe" \
			bonbombe.controllers.impl bonbombe.controllers.interf \
			bonbombe.models.impl bonbombe.models.interf \
			bonbombe.views \
			bonbombe.enums \
			bonbombe.exceptions \


cleanNommage :
	rm -rf $(NOMMAGEPATH)/*

clean : cleanNommage
	rm $(DOTCLASS)
	rm -rf $(CLASSESPATH)/*
	rm -rf $(CLIENTCLASSESPATH)/*
	rm -rf $(SERVERCLASSESPATH)/*
	rm -rf $(DOCPATH)/*