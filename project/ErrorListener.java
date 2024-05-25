import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;
import java.util.*;
import java.io.*;

public class ErrorListener extends BaseErrorListener {
     @Override
     public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new RuntimeException("line "+line+":"+charPositionInLine+" at "+ offendingSymbol+": "+msg);
    }
}
