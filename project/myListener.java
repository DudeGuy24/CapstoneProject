import java.io.File;

public class myListener extends antlrParserBaseListener{
    
    File file = null;

    /**
     * Sets the file currently being processed.
     *
     * @param file The file to set.
     */
    public void setFile(File file){
        this.file = file;
    }
    @Override public void enterGrammarSpec(antlrParser.GrammarSpecContext ctx) { 
        /* : grammarDecl prequelConstruct* rules modeSpec* EOF */
        System.out.println(" enterGrammarSpec ");
    }
	
	@Override public void exitGrammarSpec(antlrParser.GrammarSpecContext ctx) { 
        System.out.println(" exitGrammarSpec ");
    }
	
}
