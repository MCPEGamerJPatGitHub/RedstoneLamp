package net.redstonelamp.network.npd;

import lombok.Getter;
import lombok.Setter;

/*
 * This file is part of RedstoneLamp.
 *
 * RedstoneLamp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RedstoneLamp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RedstoneLamp.  If not, see <http://www.gnu.org/licenses/>.
 */

@Getter
@Setter
public class NPDException extends Exception{
    private int line;
    private String source;

    public NPDException(String msg){
        super(msg);
    }

    @Override
    public String getMessage(){
        return super.getMessage() + " at line " + line + " of " + source;
    }

    public static class MalformedParametersException extends NPDException{
        public MalformedParametersException(int min, int got){
            super("Expected at least " + min + " parameters, got " + got + " parameters only");
        }
    }

    public static class NumberFormatException extends NPDException{
        public NumberFormatException(String num){
            super("'" + num + "' is not a valid integer expression");
        }
    }
}
