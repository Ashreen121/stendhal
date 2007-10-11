package games.stendhal.server.maps.amazon.hut;

import games.stendhal.server.StendhalRPZone;
import games.stendhal.server.config.ZoneConfigurator;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.pathfinder.FixedPath;
import games.stendhal.server.pathfinder.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Builds the princess in Princess Hut on amazon island
 *
 * @author Teiv
 */
public class PrincessNPC implements ZoneConfigurator {
	//
	// ZoneConfigurator
	//

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone, attributes);
	}

	private void buildNPC(StendhalRPZone zone, Map<String, String> attributes) {
		SpeakerNPC princessNPC = new SpeakerNPC("Princess Esclara") {

			@Override
			protected void createPath() {
				List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(6, 13));
				nodes.add(new Node(14, 13));
				nodes.add(new Node(14, 4));
				nodes.add(new Node(6, 4));
				nodes.add(new Node(6, 3));
				nodes.add(new Node(4, 3));
				nodes.add(new Node(4, 7));
				nodes.add(new Node(6, 7));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
			        addGreeting("Huh, what are you doing here?");
				addReply("look","You better do not look around, it is all mine!");
				addReply("nothing","Go away and do this somewhere else but not in mine hut!");
				addJob("I do not need any help at the moment.");
				addQuest("I do not have anything to do for you.");
				addHelp("Beware of my sisters on the island, they do not like any strangers.");
				addOffer("There is nothing to offer you.");				
				addGoodbye("Goodbye, and beware of the barbarians.");
			}
		};

		princessNPC.setEntityClass("amazoness_princessnpc");
		princessNPC.setPosition(6, 13);
		princessNPC.initHP(100);
		zone.add(princessNPC);
	}
}
