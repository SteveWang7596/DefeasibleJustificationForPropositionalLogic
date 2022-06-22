package org.defeasiblejustification.model;

/**
 *
 * @author stevewang
 */
public class RationalClosureResults 
{
    private boolean entailment;
    private int ranksRemoved;
    private MinimalRankedFormulas rankedFormulas;
    
    public RationalClosureResults(boolean entailment, int ranksRemoved, MinimalRankedFormulas rankedFormulas)
    {
        this.entailment = entailment;
        this.ranksRemoved = ranksRemoved;
        this.rankedFormulas = rankedFormulas;
    }
    
    public boolean entailmentsHolds()
    {
        return this.entailment;
    }
    
    public int getRanksRemoved()
    {
        return this.ranksRemoved;
    }
    
    public MinimalRankedFormulas getMinimalRanking()
    {
        return this.rankedFormulas;
    }
    
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<< Rational Closure Result - BEGIN>>\n")
                .append("Entailment holds :\t").append(this.entailment).append("\n")
                .append("Ranked removed :\t").append(this.ranksRemoved).append("\n")
                .append("Minimal rank:\n").append(this.rankedFormulas)
                .append("<< Rational Closure Result - END >>");
        return stringBuilder.toString();
    }
    
}
