namespace MonkeyToss
{
public class Monkey
{
    // Allows us to independently vary the de-stressing algorithm
    public  delegate ulong StressManagement(ulong worryLevel);

    public int Id { get; }
    public List<ulong> Items { get;  }
    private string Formula { get; }
    public ulong DivBy { get; }
    private int TrueThrow { get; }
    private int FalseThrow { get; }
    public ulong InspectCount { get; private set;  }
    
    private string[] TokenizedExpression { get; }

    public Monkey(int id, List<ulong> items, string formula,
        ulong divBy, int trueTarget, int falseTarget)
    {
        this.Id = id;
        this.Items = new List<ulong>();
        this.Items.AddRange(items);
        this.Formula = formula;
        this.TokenizedExpression = TokenizeFormula();
        this.DivBy = divBy;
        this.TrueThrow = trueTarget;
        this.FalseThrow = falseTarget;
        this.InspectCount = 0;
    }

    public List<TossedItem> InspectItems(StressManagement deStress)
    {
        var tosses =  new List<TossedItem>();

        foreach (var item in Items)
        {
            InspectCount++;
            var newWorryLvl = CalculateWorry(item, deStress);
            var evenlyDivisible = newWorryLvl % DivBy == 0;
            var targetMonkey = evenlyDivisible ? TrueThrow : FalseThrow;
            
            tosses.Add(new TossedItem(targetMonkey, newWorryLvl));
        }

        Items.Clear();
        return tosses;
    }

    private string[] TokenizeFormula()
    {
        return Formula.Split();
    }
    
    private ulong CalculateWorry(ulong item, StressManagement deStress)
    {
        var old = item;
        var val1 = TokenizedExpression[0] == "old" ? old : UInt64.Parse(TokenizedExpression[0]);
        var op = TokenizedExpression[1];
        var val2 = TokenizedExpression[2] == "old" ? old : UInt64.Parse(TokenizedExpression[2]);

        var worryLevel = op switch
        {
            "+" => val1 + val2,
            "-" => val1 - val2,
            "*" => val1 * val2,
            _ => item
        };

        if (worryLevel / DivBy == DivBy && worryLevel % DivBy == 0)
        {
            worryLevel = DivBy;
        }

        return deStress(worryLevel);
    }
}    
}

