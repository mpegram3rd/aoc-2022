namespace MonkeyToss
{
    public class TossedItem
    {
        public int TargetMonkey { get; }
        public int WorryLevel { get;  }

        public TossedItem(int _target, int _level)
        {
            this.TargetMonkey = _target;
            this.WorryLevel = _level;
        }
    }    
}

