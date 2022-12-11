namespace MonkeyToss
{
    public class TossedItem
    {
        public int TargetMonkey { get; }
        public ulong WorryLevel { get;  }

        public TossedItem(int target, ulong level)
        {
            this.TargetMonkey = target;
            this.WorryLevel = level;
        }
    }    
}

