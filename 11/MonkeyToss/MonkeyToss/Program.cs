using System.Collections.Specialized;

namespace MonkeyToss
{
    public static class Program
    {
        
        public static void Main(string[] args)
        {
            RunSolution(20);
            RunSolution(10000);
        }

        private static void RunSolution(int iterations)
        {
            var garbIn = new StreamReader(new FileStream(@"input.txt", FileMode.Open, FileAccess.Read));
            
            // Accumulator for Least Common Multiple used in part 2. 
            var lcm = 1ul;

            var monkeys = new OrderedDictionary();
            do
            {
                var monkey = ParseMonkey(garbIn);
                monkeys.Add(monkey.Id, monkey);
                lcm *= monkey.DivBy;

                garbIn.ReadLine(); // Each monkey definition is followed by an empty line.
            } while (!garbIn.EndOfStream);

            garbIn.Close();

            // There's a different stress management strategy for problem 1 and 2. 
            // Figure out which delegate to apply based on the number of iterations. 
            Monkey.StressManagement stressManagement = iterations > 20
                ? (lvl => lvl % lcm)
                : (lvl => lvl / 3);
            
            for (var count = 0; count < iterations; count++)
            {
                foreach (Monkey monkey in monkeys.Values)
                {
                    var tosses = monkey.InspectItems(stressManagement);
                    foreach (var toss in tosses)
                    {
                        ((Monkey)monkeys[toss.TargetMonkey]!).Items.Add(toss.WorryLevel);
                    }
                }

            }

            // Order so we can extract the busiest 2 monkeys
            var busyMonkeys = monkeys.Values
                        .OfType<Monkey>()
                        .OrderByDescending(m => m.InspectCount).ToList();
            
            // Calculate and report the amount of monkey business.
            var monkeyBusiness = busyMonkeys[0].InspectCount * busyMonkeys[1].InspectCount;
            Console.WriteLine("Total Monkey Business: " + monkeyBusiness);
        }

        // Ugly as sin parser.  Very very non-resilient
        private static Monkey ParseMonkey(StreamReader garbIn)
        {
            // Parse string format "Monkey XX:"
            var idStr = garbIn.ReadLine()?["Monkey ".Length..];
            var id = int.Parse(idStr?[..^1] ?? "0");
            
            // Parse Items format "  Starting items: XX, YY, ZZ
            var itemsStr = garbIn.ReadLine()?["  Starting items: ".Length..];
            var itemsArray = itemsStr?.Split(',', StringSplitOptions.TrimEntries);
            var items = itemsArray!.Select(ulong.Parse).ToList();

            // Parse Formula format "  Operation: new = x + y"
            var formula = garbIn.ReadLine()?["  Operation: new = ".Length..];
            
            // Parse "  Test: divisible by XX"
            var divByStr = garbIn.ReadLine()?["  Test: divisible by ".Length..];
            var divBy = ulong.Parse(divByStr ?? "-1");
            
            // Parse if true/if false format "    If <condition>: throw to monkey XX"
            var targetStr = garbIn.ReadLine()?["    If true: throw to monkey ".Length..];
            var trueTarget = int.Parse(targetStr ?? "-1");
            targetStr = garbIn.ReadLine()?["    If false: throw to monkey ".Length..];
            var falseTarget = int.Parse(targetStr ?? "-1");

            return new Monkey(id, items, formula!, divBy, trueTarget, falseTarget);
        }
    }
}