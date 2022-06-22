package org.defeasiblejustification;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.defeasiblejustification.model.RationalClosureResults;
import org.defeasiblejustification.parser.DefeasibleParser;
import org.defeasiblejustification.rationalclosure.RationalClosure;
import org.defeasiblejustification.util.Utils;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 *
 * @author Steve Wang
 */
public class DefeasibleJustificationForPropositionalLogic 
{

    public static void main(String[] args) 
    {
        try 
        {
            PlBeliefSet knowledgeBase = new PlBeliefSet();
            PlFormula query = null;
            PlParser classicalParser = new PlParser();
            DefeasibleParser defeasibleParser = new DefeasibleParser(classicalParser);
            
            // args[0] = Defeasible Knowledge Base
            String inputKnowledgeBasePath = "src/main/resources/example/input/knowledgebase.txt";       //Default Knowledge Base
            if (args == null || args[0] != null)
                inputKnowledgeBasePath = args[0];
            Scanner scanner = new Scanner(new File(inputKnowledgeBasePath));
            
            while (scanner.hasNextLine())
            {
                String inputFormula = scanner.nextLine();
                if (inputFormula.contains("~>"))
                    knowledgeBase.add(defeasibleParser.parseFormula(inputFormula));
                else
                    knowledgeBase.add(classicalParser.parseFormula(inputFormula));
            }
            
            System.out.println("Knowledge Base:\n" + knowledgeBase);

            // args[1] = Query
            if (args[1] == null)
                throw new Exception("Invalid query.");
            else
                query = defeasibleParser.parseFormula(args[1]);
            
            System.out.println("Query:\n" + query.toString());
            
            computeDefeasibleExplanation(knowledgeBase, query);
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(DefeasibleJustificationForPropositionalLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void computeDefeasibleExplanation(PlBeliefSet knowledgeBase, PlFormula query) throws Exception
    {
        RationalClosureResults rationalClosure= RationalClosure.computeRationalClosure(knowledgeBase, query);
        System.out.println(rationalClosure);
        
        if (!rationalClosure.entailmentsHolds())
        {
            System.out.println("Entailment does not hold.");
            return;
        }
        
        int ranksRemoved = rationalClosure.getRanksRemoved();
        
        if (ranksRemoved == 0)
        {
            //TODO: 
            //Compute All Justifications: Return ArrayList<PlFormula>
            //Dematerialise: Return ArrayList<PlFormula>
            return;
        }
        
        int i = 0;
        
        while (i < ranksRemoved)
        {
            knowledgeBase = Utils.remove(knowledgeBase, rationalClosure.getMinimalRanking().getFinitlyRankedFormula(i));
            System.out.println("Removing rank " + i + " =====");
            System.out.println(knowledgeBase);
            i ++;
        }
        
        
        
        
        
        // If rank == 0
        // Compute All Justifications: Returns ArrayList<Justifiacitons>
        // Dematerialise: Returns ArrayList<Justifications>

        // Else
        // Remove ranking < rank 
        // Compute All Justifications: Returns ArrayList<Justifiacitons>
        // Dematerialise: Returns ArrayList<Justifications>
    }
    
}
