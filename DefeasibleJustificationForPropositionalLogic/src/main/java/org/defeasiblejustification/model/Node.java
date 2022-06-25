/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.defeasiblejustification.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.defeasiblejustification.util.Utils;
import org.tweetyproject.logics.pl.syntax.PlBeliefSet;
import org.tweetyproject.logics.pl.syntax.PlFormula;

/**
 *
 * @author SteveW
 */
public class Node 
{
    private PlBeliefSet knowledgeBase;
    private List<PlFormula> justification;
    private Map<PlFormula, Node> childrenNodes;

    public Node(PlBeliefSet knowledgeBase)
    {
        this.knowledgeBase = knowledgeBase;
    }
    public Node(PlBeliefSet knowledgeBase, List<PlFormula> justification)
    {
        this.knowledgeBase = knowledgeBase;
        this.justification = justification;
        this.childrenNodes = new HashMap<PlFormula, Node>();
        for (PlFormula formula : justification )
        {
            childrenNodes.put(formula, new Node(Utils.remove(knowledgeBase, formula)));
        }
    }
    
    public PlBeliefSet getKnowledgeBase()
    {
        return this.knowledgeBase;
    }

    public void setJustification(List<PlFormula> justification)
    {
        this.justification = justification;
    }

    public List<PlFormula> getJustification()
    {
        return this.justification;
    }

    public void addChildNode(PlFormula formula, Node node)
    {
        this.childrenNodes.put(formula, node);
    }
    
    public Node getChildNode(PlFormula formula)
    {
        return this.childrenNodes.get(formula);
    }
    
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder("<<Node>>\n");
        stringBuilder.append("<<Justification == ")
                .append(Utils.justificationToString(justification))
                .append(">>\n")
                .append("<< Child Nodes >>\n");
        if(this.childrenNodes == null)
        {
            stringBuilder.append("No childern nodes");
        }
        else
        {
            for (PlFormula key : this.childrenNodes.keySet())
            {
                stringBuilder.append(key)
                        .append(": \t")
                        .append(this.childrenNodes.get(key).toString());
            }
        }
        
        
        return stringBuilder.toString();
    }
    
}
