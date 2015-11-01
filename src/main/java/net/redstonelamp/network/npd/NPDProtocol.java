package net.redstonelamp.network.npd;

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

import lombok.Builder;
import net.redstonelamp.Player;
import net.redstonelamp.network.Protocol;
import net.redstonelamp.network.UniversalPacket;
import net.redstonelamp.request.Request;
import net.redstonelamp.response.Response;

import java.util.List;

@Builder(builderClassName = "Builder")
public class NPDProtocol extends Protocol{
    private final String name;
    private final String description;
    private final List<PacketDeclaration> packetTypes;

    @Override
    public String getName(){
        return null;
    }
    @Override
    public String getDescription(){
        return null;
    }
    @Override
    public Request[] handlePacket(UniversalPacket packet){
        return new Request[0];
    }
    @Override
    protected UniversalPacket[] _sendResponse(Response response, Player player){
        return new UniversalPacket[0];
    }
    @Override
    protected UniversalPacket[] _sendQueuedResponses(Response[] responses, Player player){
        return new UniversalPacket[0];
    }
}
