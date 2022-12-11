using System.Collections.Specialized;
using System.Diagnostics;
using MonkeyToss;

namespace MonkeyToss
{
    class Program
    {
        public static void Main(string[] args)
        {
            RunSolution();
        }

        private static void RunSolution()
        {
            var garbIn = new StreamReader(new FileStream(@"input.txt", FileMode.Open, FileAccess.Read));

            var line = String.Empty;
            var monkeys = new OrderedDictionary();
            do
            {
                var monkey = ParseMonkey(garbIn);
                if (monkey != null)
                {
                    monkeys.Add(monkey.Id, monkey);
                }

                garbIn.ReadLine(); // Each monkey definition is followed by an empty line.
            } while (!garbIn.EndOfStream);

            garbIn.Close();

            for (var count = 0; count < 20; count++)
            {
                foreach (Monkey monkey in monkeys.Values)
                {
                    var tosses = monkey.InspectItems();
                    foreach (var toss in tosses)
                    {
                        ((Monkey)monkeys[toss.TargetMonkey]).Items.Add(toss.WorryLevel);
                    }
                }
            }

            List<Monkey> busyMonkeys = monkeys.Values.OfType<Monkey>().OrderByDescending(m => m.InspectCount).ToList();
            var monkeyBusiness = busyMonkeys[0].InspectCount * busyMonkeys[1].InspectCount;
            Console.WriteLine("Total Monkey Business: " + monkeyBusiness);
        }

        // Ugly as sin parser
        private static Monkey ParseMonkey(StreamReader garbIn)
        {
            // Parse string format "Monkey XX:"
            var idStr = garbIn.ReadLine()?.Substring("Monkey ".Length);
            var id = Int32.Parse(idStr?.Substring(0, idStr.Length - 1) ?? "0");
            
            // Parse Items format "  Starting items: XX, YY, ZZ
            var itemsStr = garbIn.ReadLine()?.Substring("  Starting items: ".Length);
            var itemsArray = itemsStr?.Split(',', StringSplitOptions.TrimEntries);
            var items = new List<int>();
            foreach (var item in itemsArray!)
                items.Add(Int32.Parse(item));
            
            // Parse Formula format "  Operation: new = x + y"
            var formula = garbIn.ReadLine()?.Substring("  Operation: new = ".Length);
            
            // Parse "  Test: divisible by XX"
            var divByStr = garbIn.ReadLine()?.Substring("  Test: divisible by ".Length);
            var divBy = Int32.Parse(divByStr ?? "-1");
            
            // Parse if true/if false format "    If <condition>: throw to monkey XX"
            var targetStr = garbIn.ReadLine()?.Substring("    If true: throw to monkey ".Length);
            var trueTarget = Int32.Parse(targetStr ?? "-1");
            targetStr = garbIn.ReadLine()?.Substring("    If false: throw to monkey ".Length);
            var falseTarget = Int32.Parse(targetStr ?? "-1");

            return new Monkey(id, items, formula!, divBy, trueTarget, falseTarget);
        }
    }
}
/*
 * Monkey 0:
    Starting items: 63, 84, 80, 83, 84, 53, 88, 72
    Operation: new = old * 11
    Test: divisible by 13
      If true: throw to monkey 4
      If false: throw to monkey 7
      
      
   After each monkey inspects an item but before it tests your worry level, your relief that 
   the monkey's inspection didn't damage the item causes your worry level to be divided by 
   three and rounded down to the nearest integer.
   
   On a single monkey's turn, it inspects and throws all of the items it is holding one at 
   a time and in the order listed.
   
   When a monkey throws an item to another monkey, the item goes on the end of the recipient monkey's list.
   
   Count the total number of times each monkey inspects items over 20 rounds:
   
   Find 2 most active monkeys and multiply the number of inspections of the top 2 most active.
 */
