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

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

public class NPDParser{
    private static Map<String, Instruction> instrs = new HashMap<>();

    static{
        for(Instruction instr : new Instruction[]{
                new ProtocolNameInstruction(),
        }){
            for(String name : instr.getNames()){
                instrs.put(name, instr);
            }
        }
    }

    @Getter
    private String source;
    private final BufferedReader reader;
    private Pattern splitSpace = Pattern.compile(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // TODO validate this regex
    private int currentLine = 0;

    @Getter
    private final NPDProtocol.Builder protocolBuilder;
    @Getter
    private final List<PacketDeclaration> packetTypes;

    public NPDParser(String source, BufferedReader reader){
        this.source = source;
        this.reader = reader;
        protocolBuilder = NPDProtocol.builder().packetTypes(packetTypes = new ArrayList<>());
    }
    public NPDParser(String source, Reader reader){
        this(source, new BufferedReader(reader));
    }

    public void run(){
        String lastLine = null, line;
        try{
            while((line = reader.readLine()) != null){
                currentLine++;
                line = line.replaceAll("[ \r\n]+", " ").trim();
                int pt = line.indexOf('#');
                if(pt != -1){
                    line = line.substring(0, pt).trim();
                }
                if(line.isEmpty()){
                    continue;
                }
                if(lastLine != null){
                    line = lastLine + line;
                }
                lastLine = null;
                if(line.charAt(line.length() - 1) == '\\'){
                    lastLine = line.substring(0, line.length() - 1) + "\n";
                    continue;
                }
                dispatchInstruction(line);
            }
            if(lastLine != null){
                dispatchInstruction(lastLine.substring(0, lastLine.length() - 1));
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                reader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    private void dispatchInstruction(String line){
        List<String> args = new ArrayList<>(Arrays.asList(splitSpace.split(line, -1)));
        String instrName = args.remove(0);
        if(instrs.containsKey(instrName)){
            Instruction instr = instrs.get(instrName);
            try{
                instr.run(this, args);
            }catch(NPDException e){
                e.setSource(source);
                e.setLine(currentLine);
            }
        }
    }
}
