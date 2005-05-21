/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server;

import games.stendhal.server.entity.*;
import marauroa.common.*;
import marauroa.server.game.*;
import java.util.*;

public class RespawnPoint 
  {
  private int x;
  private int y;
  private double radius;
  
  private int maximum;
  private Creature entity;
  private List<Creature> entities;  
  
  private boolean respawning;
  final public static int TURNSTORESPAWN=180; // One minute at 300ms
  private int turnsToRespawn;
  
  private StendhalRPZone zone;

  protected static StendhalRPRuleProcessor rp;
  protected static RPWorld world;
  
  public static void setRPContext(StendhalRPRuleProcessor rpContext,RPWorld worldContext)
    {
    rp=rpContext;
    world=worldContext;
    }
  
  public RespawnPoint(int x, int y, int radius)
    {
    this.x=x;
    this.y=y;
    this.radius=radius;
    maximum=0;

    respawning=true;
    turnsToRespawn=TURNSTORESPAWN;
    }
  
  public void set(StendhalRPZone zone,Creature entity, int maximum)
    {
    this.entity=entity;
    this.entities=new LinkedList<Creature>();
    this.maximum=maximum;
    this.zone=zone;
    }
  
  public void notifyDead(Creature dead)
    {
    Logger.trace("RespawnPoint::notifyDead",">");
    if(!respawning)
      {
      respawning=true;
      turnsToRespawn=TURNSTORESPAWN;
      }
    
    entities.remove(dead);
    Logger.trace("RespawnPoint::notifyDead","<");
    }
  
  public void nextTurn()
    {
    Logger.trace("RespawnPoint::nextTurn",">");
    if(respawning) 
      {
      Logger.trace("RespawnPoint::nextTurn","D","Turns to respawn: "+turnsToRespawn);
      turnsToRespawn--;
      }
    
    if(turnsToRespawn==0)
      {
      turnsToRespawn=TURNSTORESPAWN;
      if(entities.size()<maximum) 
        {
        respawn();
        }
      else 
        {
        respawning=false;
        }
      }
    
    for(Creature creature: entities)
      {
      creature.logic();
      }
      
    Logger.trace("RespawnPoint::nextTurn","<");
    }
  
  private void respawn()
    {
    Logger.trace("RespawnPoint::respawn",">");
    try
      {
      Creature newentity=entity.getClass().newInstance();
      
      zone.assignRPObjectID(newentity);

      int ex=x;
      int ey=y;
      
      while(zone.collides(newentity,ex,ey))
        {
        ex=ex+(int)(Math.random()*(float)radius-radius/2.0);
        ey=ey+(int)(Math.random()*(float)radius-radius/2.0);        
        }
      
      newentity.setx(ex);
      newentity.sety(ey);
      newentity.setRespawnPoint(this);
      entities.add(newentity);
      
      zone.add(newentity);
      }
    catch(Exception e)    
      {
      Logger.thrown("RespawnPoint::respawn","X",e);
      }
    finally
      {
      Logger.trace("RespawnPoint::respawn","<");
      }
    }
  }
