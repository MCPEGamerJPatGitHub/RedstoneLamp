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
package net.redstonelamp.metadata;

import net.redstonelamp.nio.BinaryBuffer;

/**
 * An element implementation of a long
 * This code is based off of the code here: https://github.com/NiclasOlofsson/MiNET/tree/master/src/MiNET/MiNET/Utils
 *
 * @author RedstoneLamp Team
 */
public class MetadataLong extends MetadataElement{
    private long l;

    public MetadataLong(){
    }

    public MetadataLong(long l){
        this.l = l;
    }

    @Override
    public void fromBytes(BinaryBuffer bb){
        l = bb.getLong();
    }

    @Override
    public void toBytes(BinaryBuffer bb, int index){
        bb.putByte(getKey((byte) index));
        bb.putLong(l);
    }

    @Override
    public byte getIdentifier(){
        return 8;
    }

    @Override
    public int getLength(){
        return 8;
    }
}
