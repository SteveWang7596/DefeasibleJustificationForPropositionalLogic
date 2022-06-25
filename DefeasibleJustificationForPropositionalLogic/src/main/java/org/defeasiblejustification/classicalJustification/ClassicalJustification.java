package org.defeasiblejustification.classicalJustification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.defeasiblejustification.model.Node;
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
        SatSolver.setDefaultSolver(new Sat4jSolver());
        SatReasoner reasoner = new SatReasoner();
        
        List<PlFormula> rootJustification = computeSingleJustification(knowledgeBase, query, reasoner);
        System.out.println("Root Justification: ");
        Utils.print(rootJustification);
        
        Node rootNode = new Node(knowledgeBase, rootJustification);
        
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(rootNode);
        
        while(!queue.isEmpty())
        {
            Node node = queue.poll();
            
            System.out.println("Working on node with justification: ");
            Utils.print(node.getJustification());
            
            for( PlFormula formula : node.getJustification())
            {
                Node childNode = new Node(Utils.remove(node.getKnowledgeBase(), formula));
                List<PlFormula> childJustification = computeSingleJustification(childNode.getKnowledgeBase(), query, reasoner);
                if (childJustification != null || childJustification.isEmpty())
                {
                    childNode.setJustification(childJustification);
                    queue.add(childNode);
                }
            }
            
            
        }
        
        System.out.println("Tree:");
        System.out.println(rootNode.toString());
        return null;
    }
    
    private static List<PlFormula> computeSingleJustification(PlBeliefSet knowledgeBase, PlFormula query, SatReasoner reasoner)
    {
        List<PlFormula> result = new ArrayList<PlFormula>();
        
        if (knowledgeBase.contains(query))
        {
            result.add(query);
            return result;
        }
        
        result = expandFormulas(knowledgeBase, query, reasoner);
        System.out.println("After expand formulas:");
        Utils.print(result);
        if (result.isEmpty())
            return result;
        
        result = contractFormuls(result, query, reasoner);
        System.out.println("After contract formulas:");
        Utils.print(result);
        
        return result;
    }

    private static List<PlFormula> expandFormulas(PlBeliefSet knowledgeBase, PlFormula query, SatReasoner reasoner) 
    {
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
                PlBeliefSet resultKownledgeBase = new PlBeliefSet(result);
                if (reasoner.query(resultKownledgeBase, query))
                    return result;
                sigma = getSignature(result);
            }
        }
        return result;
    }
    
    private static List<PlFormula> findRelatedFormulas(List<Proposition> signatures, PlBeliefSet knowledgeBase)
    {
        System.out.println("For signature:");
        Utils.printPropositions(signatures);
        
        List<PlFormula> result = new ArrayList<PlFormula>();
        
        for(PlFormula formula: knowledgeBase)
        {
            if (!Collections.disjoint(getSignature(formula), signatures))
                result.add(formula);
        }
        
        System.out.println("Related formula list:");
        Utils.print(result);
        
        return result;
    }
    
    private static List<Proposition> getSignature(List<PlFormula> formulas)
    {
        List<Proposition> result = new ArrayList<Proposition>();
        for (PlFormula formula: formulas)
        {
            List<Proposition> signature = getSignature(formula);
            for (Proposition atom : signature)
            {
                if (!result.contains(atom))
                    result.add(atom);
            }
        }
        return result;
    }
    
    private static List<Proposition> getSignature(PlFormula query)
    {
        List<Proposition> result = new ArrayList<Proposition>();
        Set<Proposition> atoms = query.getAtoms();
        result.addAll(atoms);
        return result;
    }

    private static List<PlFormula> contractFormuls(List<PlFormula> result, PlFormula query, SatReasoner reasoner) 
    {
        return contractRecursive(new ArrayList<PlFormula>(), result, query, reasoner);
    }

    private static List<PlFormula> contractRecursive(List<PlFormula> support, List<PlFormula> whole, PlFormula query, SatReasoner reasoner) 
    {
        if (whole.size() == 1)
            return whole;
        
        List<List<PlFormula>> splitList = split(whole);
        List<PlFormula> left = splitList.get(0);
        List<PlFormula> right = splitList.get(1);
        
        List<PlFormula> leftUnion = Utils.union(support, left);
        PlBeliefSet leftKB = new PlBeliefSet(leftUnion);
        List<PlFormula> rightUnion = Utils.union(support, right);
        PlBeliefSet rightKB = new PlBeliefSet(rightUnion);
        
        if (reasoner.query(leftKB, query))
            return contractRecursive(support, left, query, reasoner);
        if (reasoner.query(rightKB, query))
            return contractRecursive(support, right, query, reasoner);
        
        List<PlFormula> leftPrime = contractRecursive(rightUnion, left, query, reasoner);
        List<PlFormula> leftPrimeUnion = Utils.union(support, leftPrime);
        List<PlFormula> rightPrime = contractRecursive(leftPrimeUnion, right, query, reasoner);
        
        return Utils.union(leftPrime, rightPrime);
    }
    
    private static List<List<PlFormula>> split(List<PlFormula> whole)
    {
        List<PlFormula> left = whole.subList(0, whole.size()/2);
        List<PlFormula> right = whole.subList(whole.size()/2, whole.size());
        
        List<List<PlFormula>> result = new ArrayList<>();
        result.add(left);
        result.add(right);
        
        return result;
    }
    
}
