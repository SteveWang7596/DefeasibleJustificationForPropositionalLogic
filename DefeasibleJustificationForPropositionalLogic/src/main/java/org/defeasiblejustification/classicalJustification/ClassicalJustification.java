package org.defeasiblejustification.classicalJustification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.defeasiblejustification.util.Utils;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;
import org.tweetyproject.logics.pl.syntax.Proposition;

/**
 *
 * @author stevewang
 */
public class ClassicalJustification 
{
    public static List<List<PlFormula>> computeJustification(PlBeliefSet knowledgeBase, PlFormula query)
    {
        List<PlFormula> rootJustification = computeSingleJustification(knowledgeBase, query);
        
        return null;
    }
    
    private static List<PlFormula> computeSingleJustification(PlBeliefSet knowledgeBase, PlFormula query)
    {
        List<PlFormula> result = new ArrayList<PlFormula>();
        
        if (knowledgeBase.contains(query))
        {
            result.add(query);
            return result;
        }
        
        result = expandFormulas(knowledgeBase, query);
        
        if (result.isEmpty())
            return result;
        
        result = contractFormuls(result, query);
        
        return result;
    }

    private static List<PlFormula> expandFormulas(PlBeliefSet knowledgeBase, PlFormula query) 
    {
        SatSolver.setDefaultSolver(new Sat4jSolver());
        SatReasoner reasoner = new SatReasoner();
        
        List<PlFormula> result = new ArrayList<PlFormula>();
        
        if (!reasoner.query(knowledgeBase, query))
            return new ArrayList<PlFormula>();
        else
        {
            List<PlFormula> sPrime = new ArrayList<PlFormula>();
            List<Proposition> sigma = getSignature(query);
            while (result != sPrime)
            {
                sPrime = result;
                result = Utils.union(result, findRelatedFormulas(sigma, knowledgeBase));
            }
        }
        
        return null;
    }
    
    private static List<PlFormula> findRelatedFormulas(List<Proposition> signatures, PlBeliefSet knowledgeBase)
    {
        for(PlFormula formula: knowledgeBase)
        {
            
        }
        return null;
    }
    
    private static List<Proposition> getSignature(PlFormula query)
    {
        List<Proposition> result = new ArrayList<Proposition>();
        Set<Proposition> atoms = query.getAtoms();
        result.addAll(atoms);
        return result;
    }

    private static List<PlFormula> contractFormuls(List<PlFormula> result, PlFormula query) 
    {
        return null;
    }
    
}
