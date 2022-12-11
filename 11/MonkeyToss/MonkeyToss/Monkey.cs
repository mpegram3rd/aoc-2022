using System.Collections;
using System.ComponentModel.Design;

namespace MonkeyToss
{
public class Monkey
{
    public int Id { get; }
    public List<int> Items { get;  }
    public string Formula { get; }
    public int DivBy { get; }
    public int TrueThrow { get; }
    public int FalseThrow { get; }
    public int InspectCount { get; set;  }
    
    private string[] _tokenizedExpression { get; }

    public Monkey(int _id, List<int> _items, string _formula,
        int _divBy, int _trueTarget, int _falseTarget)
    {
        this.Id = _id;
        this.Items = new List<int>();
        this.Items.AddRange(_items);
        this.Formula = _formula;
        this._tokenizedExpression = TokenizeFormula();
        this.DivBy = _divBy;
        this.TrueThrow = _trueTarget;
        this.FalseThrow = _falseTarget;
        this.InspectCount = 0;
    }

    public List<TossedItem> InspectItems()
    {
        var tosses =  new List<TossedItem>();

        foreach (var item in Items)
        {
            InspectCount++;
            var newWorryLvl = CalculateWorry(item);
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
    
    private int CalculateWorry(int item)
    {
        var old = item;
        var val1 = _tokenizedExpression[0] == "old" ? old : Int32.Parse(_tokenizedExpression[0]);
        var op = _tokenizedExpression[1];
        var val2 = _tokenizedExpression[2] == "old" ? old : Int32.Parse(_tokenizedExpression[2]);

        var worryLevel = item;
        if (op == "+")
            worryLevel = val1 + val2;
        if (op == "-")
            worryLevel = val1 - val2;
        if (op == "*")
            worryLevel = val1 * val2;

        return worryLevel / 3;
    }
    
}    
}

