using System;

namespace Pull.Months
{
    public struct Month
    {
        private readonly int year;
        private readonly int month;

        public Month(DateTime date)
        {
            this.year = date.Year;
            this.month = date.Month;
        }

        public int DaysInMonth
        {
            get
            {
                return DateTime.DaysInMonth(year, month);
            }
        }

        public override bool Equals(object obj)
        {
            return obj is Month && this == (Month)obj;
        }

        public override int GetHashCode()
        {
            return year.GetHashCode() ^ month.GetHashCode();
        }

        public static bool operator ==(Month x, Month y)
        {
            return x.year == y.year
                && x.month == y.month;
        }

        public static bool operator !=(Month x, Month y)
        {
            return !(x == y);
        }
    }
}
