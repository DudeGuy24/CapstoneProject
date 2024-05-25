import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.File;
import java.lang.Exception;
import java.io.IOException;

public class Main {
    /* recur over a directory */
    private static ArrayList<File> recur(File[] arr, ArrayList<File> files) {
        for(File f : arr) {
            if(f.isFile()) {
                files.add(f);
            } else if (f.isDirectory() & f.list().length > 0) {
                recur(f.listFiles(), files);
            }
        }
        return files;
    }
    /* */
    private static void handleDir(File dir) {
        ArrayList<File> fileList = new ArrayList<File>();
        File[] arr = dir.listFiles();
        fileList = recur(arr, new ArrayList<File>());

         if (!fileList.isEmpty()) {
            for(File file : fileList) {
                parseInput(file);
            }
        }
    }

    private static void parseInput(File file) {
        FileInputStream fis = null;
        CharStream in = null;
        antlrLexer lexer = null;
        CommonTokenStream tokens = null;
        antlrParser parser = null;
        
        try {
            fis = new FileInputStream(file);
            in = CharStreams.fromStream(fis);

            lexer = new antlrLexer(in);
            /* disable antlr lexer error listeners */
	        lexer.removeErrorListeners();  
	        lexer.addErrorListener(new ErrorListener());

            tokens = new CommonTokenStream(lexer);

            parser = new antlrParser(tokens);
            /* disable antlr error listeners */
            parser.removeErrorListeners();
            parser.addErrorListener(new ErrorListener());

            /* start building tree from start rule */
            ParseTree tree = parser.grammarSpec();

            myListener listener = new myListener();
            listener.setFile(file);

            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(listener, tree);

            System.out.println(file.getPath()+" passes");

        } catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            System.out.println(file.getPath()+ " fails");
        } finally  {
            try {
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        File testInput = null; 
        if(args[0] != null) {
            testInput = new File(args[0]);
        }

        if(testInput.exists() && testInput.isDirectory()) {
            handleDir(testInput);
        } else if (testInput.isFile()) {
            parseInput(testInput);
        } else {
            System.out.println("Wrong Input");
            System.exit(1);
        }
    }
}
