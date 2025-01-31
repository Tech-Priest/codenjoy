﻿/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2020 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Battlecity.API
{
    public struct Point
    {
        public readonly int X;
        public readonly int Y;

        public Point(int x, int y)
        {
            X = x;
            Y = y;
        }

        public bool IsOutOf(int size) => 
            X >= size || Y >= size || X < 0 || Y < 0;

        #region Shifts

        public Point ShiftLeft(int delta = 1) => new Point(X - delta, Y);

        public Point ShiftRight(int delta = 1) => new Point(X + delta, Y);

        public Point ShiftTop(int delta = 1) => new Point(X, Y + delta);

        public Point ShiftBottom(int delta = 1) => new Point(X, Y - delta);

        #endregion

        #region Operators overloading

        public static bool operator ==(Point p1, Point p2)
        {
            if (ReferenceEquals(p1, p2))
                return true;

            if (ReferenceEquals(p1, null) || ReferenceEquals(p2, null))
                return false;

            return p1.X == p2.X && p1.Y == p2.Y;
        }

        public static bool operator !=(Point p1, Point p2) => !(p1 == p2);

        #endregion

        #region Object methods overloading

        public override string ToString() => $"[{X},{Y}]";

        public override bool Equals(object obj)
        {
            if (obj == null) 
                return false;

            if (!(obj is Point)) 
                return false;

            Point that = (Point)obj;
            return that.X == this.X && that.Y == this.Y;
        }

        public override int GetHashCode() => X.GetHashCode() ^ Y.GetHashCode();

        #endregion
    }
}
