using System;
using System.Collections.Generic;
using System.IO;
using System.Text.RegularExpressions;

namespace Stacking
{
    class Program
    {
        private static readonly Regex Rx = new Regex(@"\d+", RegexOptions.Compiled);
        
        // Use delegates (as Lambdas) to support different strategies for how the mover moves crates.
        private  delegate void CrateMover(int howMany, int from, int to, Stack<char>[] stacks);

        private static void Main(string[] args)
        {
            RunSolution(1, CrateMover9000);
            RunSolution(2, CrateMover9001);
        }

        // Going to be lazy here and just let it full parse/process each time. 
        private static void RunSolution(int problemNum, CrateMover crateMover)
        {
            var garbIn = new StreamReader(new FileStream(@"input.txt", FileMode.Open, FileAccess.Read));
            
            var line = String.Empty;
            var stacks = new Stack<char>[9]
            {
                new Stack<char>(), new Stack<char>(), new Stack<char>(),
                new Stack<char>(), new Stack<char>(), new Stack<char>(),
                new Stack<char>(), new Stack<char>(), new Stack<char>()
            };
            InitStacks(garbIn, stacks);
            
            do
            {
                ProcessMove(line, stacks, crateMover);
            } while ((line = garbIn.ReadLine()) != null);

            Console.Write("Problem " + problemNum + ": Top of Each Stack = ");
            foreach (var stack in stacks)
            {
                Console.Write(stack.Count > 0 ? stack.Peek(): " ");
            }
            Console.WriteLine();
            garbIn.Close();
        }

        // CrateMover9000 can only move 1 crate at a time.
        // This causes items to be stacked up in the inverted order in which they were removed. 
        private static void CrateMover9000(int howMany, int from, int to, Stack<char>[] stacks)
        {
            for (var count = 0; count < howMany; count++)
            {
                var item = stacks[from].Pop();
                stacks[to].Push(item);
            }

        }
        
        // CrateMover9001 can move whole stacks at once so stack items stay in order
        // We use recursion to handle this.  The process looks like this:
        // - We pop things off the stack from top to bottom one at a time
        // - Check to see if we've popped the right number (recursive exit condition)
        // - Then we push each onto the new stack as we unwind the recursion. 
        private static void CrateMover9001(int howMany, int from, int to, Stack<char>[] stacks)
        {
            // Keep recursing here until there's no more crates to pickup (exit condition)
            if (howMany > 0)
            {
                // Pre-condition... Pull a crate off the top of the stack (and hang on to it)
                var item = stacks[from].Pop();
                
                // Call recursively
                CrateMover9001(howMany - 1, from, to, stacks);
                
                // Post-condition (as we unwind) push the item onto the target stack. 
                // (builds from the bottom up)
                stacks[to].Push(item);
            }
        }

        // Parses the moves and then executes them using whatever CrateMover strategy was provided
        private static void ProcessMove(string line, Stack<char>[] stacks, CrateMover crateMover)
        {
            MatchCollection nums = Rx.Matches(line);
            if (nums.Count == 3)
            {
                var howMany = Int32.Parse(nums[0].Value);
                var from = Int32.Parse(nums[1].Value) - 1; // offset by 1 for 0 indexed array
                var to = Int32.Parse(nums[2].Value) - 1; // offset by 1 for 0 indexed array
                crateMover(howMany, from, to, stacks);
            }
        }
        
        // Recursively reads file so we can push items onto the stacks in the proper order
        // when we unwind the recursive callstack
        private static void InitStacks(StreamReader garbIn, Stack<char>[] stacks)
        {
            var line = garbIn.ReadLine();
            // Exit condition.. we keep processing until we find a line that doesn't contain a '['
            if (line!.Contains('['))
            {
                // Call recursively again if we didn't find a line that contains a  '['
                InitStacks(garbIn, stacks);
                
                // POST processing (after recursive calls) means we're processing the last line we found 
                // First.. So we can push the bottom of the stack in first and build from there as we
                // unwind the recursion.
                var chars = line!.ToCharArray();
                var index = 1;
                
                while (index < chars.Length)
                {
                    var item = chars[index];
                    if (item is >= 'A' and <= 'Z')
                    {
                        stacks[index / 4].Push(item);
                    }

                    index += 4; // Each item appears at a +4 offset
                }
            }

        }
    }
}