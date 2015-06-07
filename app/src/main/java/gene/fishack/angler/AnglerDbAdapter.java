package gene.fishack.angler;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AnglerDbAdapter {
	
	private SQLiteDatabase database;
	private OpenHelper DbHelper;

	private final Context ADbAContext;
	
	private static final String DATABASE_NAME = "angler.db";
	private static final int DATABASE_VERSION = 19;

	public static final String TABLE_lakes = "lakes";
	public static final String TABLE_fish = "fish";
	public static final String TABLE_lakesFish = "lakesFish";

	public static final String CREATE_TABLE_lakes = "CREATE TABLE " + TABLE_lakes + " (_id INTEGER PRIMARY KEY,  name TEXT,  map_resID INTEGER,  satImage_resID INTEGER, depthImage_resID INTEGER, latitude REAL, longitude REAL,  maxDepth INTEGER,  meanDepth INTEGER,  area INTEGER,  elevation INTEGER,  boatInfo TEXT,  fishingInfo TEXT,  iceFishingInfo TEXT);";
	public static final String CREATE_TABLE_fish = "CREATE TABLE " + TABLE_fish + " (_id INTEGER PRIMARY KEY, fish_resID INTEGER, name TEXT, other_names TEXT, scientific_name TEXT, origin TEXT, adult_size TEXT, identification TEXT);";
	public static final String CREATE_TABLE_lakesFish = "CREATE TABLE " + TABLE_lakesFish + " (_id INTEGER PRIMARY KEY ,  lakeid INTEGER,  fishid INTEGER );";
	
	public static final String [] INSERT_DATA_lakes = new String [] {
		"INSERT INTO LAKES values (1,'Allatoona Lake',0,0," + R.drawable.allatoona_lake + ",44.957528,-70.982218,60,31,6700,1518,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (2,'Andrews Lake',0,0," + R.drawable.andrews_lake + ",44.896713,-70.990383,32,11,12,2056,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (3,'Bartletts Ferry Lake',0,0," + R.drawable.bartletts_ferry_lake + ",44.743176,-70.959371,110,29,471,1389,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (4,'Blue Ridge Lake',0,0," + R.drawable.blue_ridge_lake + ",45.216643,-70.548539,27,6,22,1407,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (5,'Carters Lake',0,0," + R.drawable.carters_lake + ",44.903914,-70.410228,17,6,32,1337,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (6,'Lake Blackshear',0,0," + R.drawable.lake_blackshear + ",44.900305,-70.612432,52,22,543,1729,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (7,'Lake Burton',0,0," + R.drawable.lake_burton + ",44.897112,-70.939117,72,20,179,1489,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
		"INSERT INTO LAKES values (8,'Lake Lanier',0,0," + R.drawable.lake_lanier + ",45.260890,-70.776259,20,8,20,1991,'All fish species are OPEN to fishing at this time','Use of electronic devices, explosives, poisons, or firearms to take fish','It is unlawful to possess more than a total of 50 individuals of all the game fish');",
//		"INSERT INTO LAKES values (9,'Beaver Pond (Twp D)',0,0," + R.drawable.beaver_pond_twp_d + ",44.811641,-70.689815,19,5,20,1971,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (10,'Big Island Pond',0,0," + R.drawable.big_island_pond + ",45.283989,-70.753320,40,18,350,2151,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (11,'Birch Pond',0,0," + R.drawable.birch_pond + ",44.643082,-70.556894,15,6,11,1212,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (12,'Blanchard Pond',0,0," + R.drawable.blanchard_pond + ",45.270671,-70.597759,27,10,9,1351,'Boat Launch - Hand Carry','Ind Ter','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (13,'Bugeye Pond',0,0," + R.drawable.bugeye_pond + ",45.309236,-70.609644,10,6,6,1291,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (14,'Bunker Pond',0,0," + R.drawable.bunker_pond + ",44.664178,-70.614598,5,3,35,1065,'Boat Launch - Hand Carry','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (15,'Butler Pond',0,0," + R.drawable.butler_pond + ",45.293501,-70.441020,24,3,45,1536,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (16,'C Pond',0,0," + R.drawable.c_pond + ",44.718345,-70.884226,36,10,173,1292,'No Boat Launch','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (17,'Chain of Ponds',0,0," + R.drawable.chain_of_ponds + ",45.323198,-70.640110,106,24,700,1280,'No Boat Launch','General','Ice Fishing: Jan 1 to Mar 31');",
//		"INSERT INTO LAKES values (18,'Chase Pond',0,0," + R.drawable.chase_pond + ",45.259928,-70.555462,21,'unk',14,1190,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (19,'Cloutman Pond',0,0," + R.drawable.cloutman_pond + ",45.009670,-70.804862,5,3,20,1473,'No Boat Launch','General','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (20,'Cow Pond',0,0," + R.drawable.cow_pond + ",45.037212,-70.637083,6,4,62,1804,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (21,'Cranberry Pond',0,0," + R.drawable.cranberry_pond + ",44.859536,-70.947948,9,3,100,1408,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (22,'Cupsuptic Lake ',0,0," + R.drawable.cupsuptic_lake + ",45.010785,-70.853468,123,60,16300,1467,'Boat Launch - Trailerable','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (23,'Cupsuptic Pond',0,0," + R.drawable.cupsuptic_pond + ",45.224446,-70.873546,3,3,20,2471,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (24,'Day Mountain Pond',0,0," + R.drawable.day_mountain_pond + ",44.776901,-70.248262,27,11,12,1379,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (25,'Dodge Pond',0,0," + R.drawable.dodge_pond + ",44.990716,-70.711667,51,25,230,1521,'Boat Launch - Trailerable','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (26,'Dutton (Shiloh) Pond',0,0," + R.drawable.dutton_shiloh_pond + ",45.012566,-70.207520,39,20,20,1300,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (27,'East Richardson Pond #1',0,0," + R.drawable.east_richardson_pond1 + ",44.930213,-70.883956,20,11,85,1778,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (28,'East Richardson Pond #2',0,0," + R.drawable.east_richardson_pond2 + ",44.924498,-70.887189,15,9,54,1775,'No Boat Launch','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (29,'Eddy Pond',0,0," + R.drawable.eddy_pond + ",44.918278,-70.525658,22,6,9,2634,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (30,'Ellis (Roxbury) Pond',0,0," + R.drawable.ellis_roxbury_pond + ",44.660929,-70.675771,43,10,920,830,'Boat Launch - Trailerable','General','Ice Fishing: Ice Fishing: Dec 1 to Apr 30');",
//		"INSERT INTO LAKES values (31,'Flagstaff lake',0,0," + R.drawable.flagstaff_lake + ",45.156238,-70.439798,50,18,20300,1147,'Boat Launch - Trailerable','General','Ice Fishing: Dec 1 to Apr 30');",
//		"INSERT INTO LAKES values (32,'Flatiron Pond',0,0," + R.drawable.flatiron_pond + ",45.068508,-70.696726,23,6,30,1972,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (33,'Goodwin Pond',0,0," + R.drawable.goodwin_pond + ",44.941591,-70.848520,7,5,7,1473,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (34,'Grants Pond',0,0," + R.drawable.grants_pond + ",45.288512,-70.783354,6,4,20,2120,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (35,'Greely Pond',0,0," + R.drawable.greely_pond + ",45.022942,-70.615220,2,1,42,1590,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (36,'Greenbush Pond',0,0," + R.drawable.greenbush_pond + ",45.249013,-70.520662,22,'unk',21,1188,'No Boat Launch','General','Ice Fishing: Dec 1 to Apr 30');",
//		"INSERT INTO LAKES values (37,'Grindstone Pond',0,0," + R.drawable.grindstone_pond + ",45.002997,-70.204670,30,12,6,1302,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (38,'Gull Pond',0,0," + R.drawable.gull_pond + ",44.989275,-70.637464,40,15,281,1590,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (39,'Haley Pond',0,0," + R.drawable.haley_pond + ",44.971680,-70.628887,23,8,170,1524,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing: Jan 1 to Mar 31 No Live Fish As Bait');",
//		"INSERT INTO LAKES values (40,'Harvey Pond',0,0," + R.drawable.harvey_pond + ",44.854975,-70.502560,8,5,10,985,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (41,'Jim Pond',0,0," + R.drawable.jim_pond + ",45.266015,-70.505326,125,40,320,1231,'Boat Launch - Trailerable','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (42,'Johns Pond',0,0," + R.drawable.johns_pond + ",45.074482,-70.771893,49,24,267,1754,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (43,'Kamankeag Pond',0,0," + R.drawable.kamankeag_pond + ",45.032910,-70.755800,28,15,40,1957,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (44,'Kennebago Lake',0,0," + R.drawable.kennebago_lake + ",45.096893,-70.723524,116,11,1700,1781,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (45,'L Pond',0,0," + R.drawable.l_pond + ",45.268942,-70.742640,14,69,100,2110,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (46,'Ledge Pond',0,0," + R.drawable.ledge_pond + ",44.913063,-70.538528,24,10,6,2930,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (47,'Lincoln Pond',0,0," + R.drawable.lincoln_pond + ",45.059679,-70.948141,95,30,340,1753,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (48,'Little Beaver Pond',0,0," + R.drawable.little_beaver_pond + ",44.902937,-70.955145,51,16,50,1490,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (49,'Little Ellis Pond',0,0," + R.drawable.little_ellis_pond + ",44.704680,-70.670096,41,6,297,1135,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (50,'Little Greely Pond',0,0," + R.drawable.little_greely_pond + ",45.028528,-70.617494,12,25,15,1611,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (51,'Little Hammond Pond',0,0," + R.drawable.little_hammond_pond + ",45.053634,-70.225980,8,3,3,1250,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (52,'Little Island Pond',0,0," + R.drawable.little_island_pond + ",45.270839,-70.783970,38,11,50,2025,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (53,'Little Jim Pond',0,0," + R.drawable.little_jim_pond + ",45.263129,-70.506078,37,12,64,1733,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (54,'Little Kennebago Lake',0,0," + R.drawable.little_kennebago_lake + ",45.134134,-70.771295,56,28,190,1785,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (55,'Little Northwest Pond',0,0," + R.drawable.little_northwest_pond + ",45.300983,-70.795503,7,5,10,2130,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (56,'Little Swift River Pond',0,0," + R.drawable.little_swift_river_pond + ",44.850534,-70.589289,22,10,15,2412,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (57,'Little Tea Pond',0,0," + R.drawable.little_tea_pond + ",45.238470,-70.540088,20,10,3,1457,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (58,'Long Pond',0,0," + R.drawable.long_pond + ",44.843154,-70.675518,114,40,254,2330,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (59,'Long Pond (Seven Ponds)',0,0," + R.drawable.long_pond_seven_ponds + ",45.255200,-70.776174,15,8,45,1986,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (60,'Loon Lake',0,0," + R.drawable.loon_lake + ",45.023926,-70.655644,50,21,192,1713,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (61,'Lufkin Pond',0,0," + R.drawable.lufkin_pond + ",44.841090,-70.463330,28,12,47,915,'No Boat Launch','General','Ice Fishing: Jan 1 to Mar 31');",
//		"INSERT INTO LAKES values (62,'Midway Pond',0,0," + R.drawable.midway_pond + ",44.930133,-70.542987,29,11,7,2705,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (63,'Mooselookmeguntic Lake',0,0," + R.drawable.mooselookmeguntic_lake + ",44.978130,-70.788508,123,60,16300,1467,'Boat Launch - Trailerable','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (64,'Mount Blue Pond',0,0," + R.drawable.mount_blue_pond + ",44.765603,-70.335632,38,17,134,1175,'Boat Launch - Hand Carry','General','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (65,'Mountain Pond',0,0," + R.drawable.mountain_pond + ",44.895247,-70.643570,36,13,40,2363,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (66,'Moxie Pond',0,0," + R.drawable.moxie_pond + ",44.845377,-70.685733,24,7,6,2330,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (67,'Mud Pond (Jim Pond Twp)',0,0," + R.drawable.mud_pond_jim_pond_twp + ",45.251676,-70.494482,22,'unk',14,1220,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (68,'Mud Pond (Lower Cupsuptic)',0,0," + R.drawable.mud_pond_lower_cupsuptic_ + ",45.027771,-70.817256,7,4,6,1510,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (69,'Mud Pond (Twp 6 N of Weld)',0,0," + R.drawable.mud_pond_twp_6_n_of_weld + ",44.844755,-70.487401,9,5,9,1156,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (70,'Northwest Pond',0,0," + R.drawable.northwest_pond + ",45.298455,-70.788107,25,12,45,2190,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (71,'Otter Pond (Parmachenee)',0,0," + R.drawable.otter_pond_parmachenee + ",45.182488,-70.980520,18,12,14,1637,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (72,'Parmachenee Lake',0,0," + R.drawable.parmachenee_lake + ",45.147795,-70.984901,93,36,912,1633,'Boat Launch - Trailerable','Trolling and casting with flies only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (73,'Pepperpot Pond',0,0," + R.drawable.pepperpot_pond + ",44.917554,-70.909759,23,11,50,1500,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (74,'Perry Pond',0,0," + R.drawable.perry_pond + ",44.876297,-70.502363,38,17,6,1634,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (75,'Pinnacle Pond',0,0," + R.drawable.pinnacle_pond + ",44.965661,-70.172407,42,14,4,600,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (76,'Pond in the River',0,0," + R.drawable.pond_in_the_river + ",44.762494,-70.928459,40,19,512,1403,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (77,'Quill Pond',0,0," + R.drawable.quill_pond + ",45.028495,-70.534831,5,2,6,2034,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (78,'Quimby Pond',0,0," + R.drawable.quimby_pond + ",44.985296,-70.743774,12,6,165,1682,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (79,'Rangeley Lake',0,0," + R.drawable.rangeley_lake + ",44.924764,-70.618295,149,60,6000,1518,'Boat Launch - Trailerable','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (80,'Redington Pond (Carrabasset)',0,0," + R.drawable.redington_pond_carrabasset + ",45.062890,-70.243413,6,4,64,1421,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (81,'Redington Pond (Redington)',0,0," + R.drawable.redington_pond_redington + ",44.984256,-70.420074,11,3,36,1616,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (82,'Richardson Lakes',0,0," + R.drawable.richardson_lakes + ",44.820035,-70.875595,108,44,7751,1465,'Boat Launch - Trailerable','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (83,'Richardson Pond',0,0," + R.drawable.richardson_pond + ",44.923230,-70.919460,41,17,423,1506,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (84,'Rock Pond',0,0," + R.drawable.rock_pond + ",44.937204,-70.537452,15,6,5,2720,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (85,'Rock Pond (Chain of Ponds)',0,0," + R.drawable.rock_pond_chain_of_ponds + ",45.297132,-70.754404,6,4,26,2175,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (86,'Ross Pond',0,0," + R.drawable.ross_pond + ",44.978992,-70.671316,8,4,26,1595,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (87,'Round Mountain Pond',0,0," + R.drawable.round_mountain_pond + ",45.266650,-70.645973,'unk','unk',75,1789,'No Boat Launch','Ind Ter','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (88,'Round Pond',0,0," + R.drawable.round_pond + ",45.001456,-70.723043,50,22,166,1544,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (89,'Round Pond (Twp E)',0,0," + R.drawable.round_pond_twp_e + ",44.834088,-70.672133,8,5,42,2376,'Boat Launch - Hand Carry','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (90,'Rump Pond',0,0," + R.drawable.rump_pond + ",45.210049,-71.016402,14,8,35,1686,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (91,'Sabbath Day Pond',0,0," + R.drawable.sabbath_day_pond + ",44.839492,-70.657669,73,21,57,2372,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (92,'Saddleback Lake',0,0," + R.drawable.saddleback_lake + ",44.962291,-70.563664,14,9,358,1747,'Boat Launch - Hand Carry','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (93,'Saddleback Pond',0,0," + R.drawable.saddleback_pond + ",44.913041,-70.499386,13,7,13,2130,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (94,'Sandy River Pond - Lower',0,0," + R.drawable.sandy_river_pond_lower_3  + ",44.896679,-70.544626,21,6,17,1700,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (95,'Sandy River Pond - Middle',0,0," + R.drawable.sandy_river_pond_middle_3 + ",44.898375,-70.553217,58,18,70,1700,'Boat Launch - Hand Carry','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (96,'Sandy River Pond - Upper',0,0," + R.drawable.sandy_river_pond_upper_3 + ",44.895440,-70.566011,14,5,28,1700,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (97,'Schoolhouse Pond',0,0," + R.drawable.schoolhouse_pond + ",44.774114,-70.263706,20,7,3,1480,'Boat Launch - Hand Carry','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (98,'Secret Pond',0,0," + R.drawable.secret_pond + ",45.250937,-70.774631,9,4,10,2030,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (99,'Shallow Pond',0,0," + R.drawable.shallow_pond + ",45.261064,-70.533798,6,3,65,1197,'No Boat Launch','General','Ice Fishing: Dec 1 to Apr 30');",
//		"INSERT INTO LAKES values (100,'Snow Mountain Pond',0,0," + R.drawable.snow_mountain_pond + ",45.276920,-70.702315,7,'unk',10,2816,'No Boat Launch','Ind Ter','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (101,'South Boundary Pond',0,0," + R.drawable.snow_mountain_pond + ",45.291497,-70.796499,5,4,10,2136,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (102,'South Pond',0,0," + R.drawable.south_pond + ",44.867788,-70.557298,29,11,9,2174,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (103,'Spencer Pond',0,0," + R.drawable.spencer_pond + ",44.821767,-70.688034,30,8,15,2160,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (104,'Stetson Pond',0,0," + R.drawable.stetson_pond + ",44.848393,-70.477278,36,14,11,1151,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
		"INSERT INTO LAKES values (105,'Sturtevant Pond',0,0," + R.drawable.sturtevant_pond + ",44.868109,-71.021640,58,23,518,1247,'No Boat Launch','No Live Fish As Bait','Ice Fishing: Jan 1 to Mar 31 No Live Fish As Bait');",
		"INSERT INTO LAKES values (106,'Sunday Pond',0,0," + R.drawable.sunday_pond + ",44.798906,-70.952684,50,24,30,1410,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (107,'Swift River Pond',0,0," + R.drawable.swift_river_pond + ",44.841399,-70.592246,10,5,10,2216,'No Boat Launch','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (108,'Tea Pond',0,0," + R.drawable.tea_pond + ",45.232920,-70.528988,115,45,90,1313,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (109,'Tim Pond & Mud Pond',0,0," + R.drawable.tim_pond_and_mud_pond + ",45.162536,-70.625419,46,13,320,2012,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (110,'Toothaker Pond',0,0," + R.drawable.toothaker_pond + ",44.868729,-70.397221,20,11,30,795,'No Boat Launch','No Live Fish As Bait','Ice Fishing: Jan 1 to Mar 31 No Live Fish As Bait 2 Lines per person');",
//		"INSERT INTO LAKES values (111,'Tufts Pond',0,0," + R.drawable.tufts_pond + ",44.997199,-70.200266,43,9,53,1268,'No Boat Launch','Artificial Lures Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (112,'Tumbledown Pond',0,0," + R.drawable.tumbledown_pond + ",44.749750,-70.541729,22,8,9,2674,'No Boat Launch','No Live Fish As Bait','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (113,'Umbagog Lake',0,0," + R.drawable.umbagog_lake + ",44.805285,-71.027154,48,14,7850,1246,'No Boat Launch','General','Ice Fishing: Jan 1 to Mar 31 No LL Salmon');",
//		"INSERT INTO LAKES values (114,'Webb Lake',0,0," + R.drawable.webb_lake + ",44.701967,-70.452740,42,24,2146,677,'Boat Launch - Trailerable','General','Ice Fishing: Jan 1 to Mar 31');",
//		"INSERT INTO LAKES values (115,'Wells Pond',0,0," + R.drawable.wells_pond + ",45.180133,-70.980534,16,10,7,1634,'No Boat Launch','Fly Fishing Only','Ice Fishing Closed');",
//		"INSERT INTO LAKES values (116,'Wethern (Welhern) Pond',0,0," + R.drawable.wethern_welhern_pond + ",45.213101,-70.493597,16,7,9,1187,'No Boat Launch','General','Ice Fishing: Dec 1 to Apr 30');"
		};
	
	
	public static final String [] INSERT_DATA_fish = new String [] {
		"INSERT INTO fish VALUES (1 , " + R.drawable.landlocked_salmon + ", 'Landlocked Salmon', 'Sebago Salmon, Quananiche', 'Salmo salar', 'Native', 'Average size is 16-18 inches and 1-1 1/2 pounds, but 3-5 pound fish are not uncommon.', 'Adults are generally silvery with a slightly forked tail and small X-shaped markings on the back and upper sides. Juvenile salmon have a dark red spot between each pair of parr marks. Mature males develop a ''kype'', or hooked jaw, during the spawning season.');",
		"INSERT INTO fish VALUES (2 , " + R.drawable.crappie_black + ", 'Crappie', 'Black Crappie, White Crappie, Crappie', 'Pomoxis nigromaculatus', 'Native', 'With a compressed body, small head and arched back, the black crappie is silvery-green to yellowish, with large dorsal and anal fins of almost identical shape and size. It has a large mouth with an upper jaw extending under the eye. It has many dark spots on its sides and fins, which become more mottled toward the back. To differentiate between a black crappie and a white crappie, count the dorsal spines. The black crappie has seven to eight dorsal spines, while the white crappie has only five to six.', 'Color is variable,The black crappie has seven to eight dorsal spines, while the white crappie has only five to six.');",
		"INSERT INTO fish VALUES (3 , " + R.drawable.rainbow_smelt + ", 'Rainbow Smelt', 'Smelt, Freshwater Smelt, American Smelt', 'Osmerus mordax', 'Native', 'Maine smelt can reach a wide variety of sizes. In many lakes smelt grow to a maximum size of 3-5 inches, depending on food, competition, and growing conditions. Smelt in northern Maine lakes commonly average 6-8 inches in length, and some Maine lakes grow smelt as large as 14 inches.', 'Their body is long and slender with a large elongated, pointed mouth. They have strong conical teeth on jaws and tongue, with fewer than 75 scales along lateral line. Color is silver with a green back, and iridescent purple, pink, and blue reflections on the side. An adipose fin is present.');",
		"INSERT INTO fish VALUES (4 , " + R.drawable.chain_pickerel + ", 'Chain Pickerel', 'Pickerel, Chainsides, Eastern Pickerel', 'Esox niger', 'Native', 'Most of the pickerel caught in Maine are age 3-5, at which time they range in length from 14-19 inches. Productive waters grow good numbers of 2-3 pound fish, along with occasional 4 pound fish. The state record, caught in 1992, weighed 6 pounds, 13 ounces.', 'Pickerel are a member of the pike family. The fish is green with the sides prominently marked by yellow-green areas broken by dark, interconnecting lines resembling the links of a chain. The jaws are elongated containing large, sharp teeth; the large dorsal fin is located way back towards the caudal fin, which is forked. Know the difference between Chain Pickerel and Northern Pike.');",
		"INSERT INTO fish VALUES (5 , " + R.drawable.hornpout + ", 'Bullhead', 'Bullhead, Hornpout, Catfish', 'Ameiurus nebulosus', 'Native', 'In Maine, adult bullheads are usually about 10 inches long and weigh less than one pound. Fish up to 14 inches are occasionally caught, but fish 18 inches and larger are rare in Maine.', 'As the name implies, the brown bullhead is dark brown to olive green on the back, with mottled sides, and a creamy white belly. Individuals having white patches on their sides and back are common in some Maine waters. Brown bullheads have a thick rounded body, a broad, somewhat flattened head with a distinctive set of ''whiskers'' around the mouth called barbels. The dorsal and pectoral fins have sharp saw tooth spines at their base that can be locked in an erect position. The caudal fin is square and there is a pronounced adipose fin. They have no scales on their skin.');",
		"INSERT INTO fish VALUES (6 , " + R.drawable.largemouth_bass + ", 'Largemouth Bass', 'Black Bass, Largemouth', 'Micropterus salmoides', 'Introduced', 'The typical largemouth bass in Maine is 12-16 inches long and weigh 1-3 pounds, with occasional fish up to 7-8 pounds.', ' Largemouths are dark olive green on the back with light green sides shading to a white belly. A dark mottled band extends along the sides. The upper part of the mouth extends past the eye. Smallmouth bass are similar in appearance, but the upper jaw ends below the eye.');",
		"INSERT INTO fish VALUES (7 , " + R.drawable.lake_trout + ", 'Lake Trout', 'Laker, Grey Trout, Mackinaw', 'Salvelinus namaycush', 'Native', 'In most waters, lake trout commonly reach lengths of 18 to 24 inches and weights of 2 to 4 pounds. They are among the longest lived and largest freshwater game fish, often living 20 years or more and attaining sizes of over 30 inches and 10 pounds.', 'Lake trout have a typical trout-shaped body covered with light spots on a darker background of green or grayish brown. They are closely related to the brook trout, but lack the bright coloration and can be distinguished by a deeply forked tail.');",
		"INSERT INTO fish VALUES (8 , " + R.drawable.yellow_perch + ", 'Yellow Perch', 'Perch, Lake Perch, American Perch', 'Perca flavescens', 'Native', 'Yellow perch normally range from 6 to 12 inches in length and weigh from 1/4 to 1 pound. Larger yellow perch, up to 15 inches and 1.6 pounds, are occasionally caught in Maine.', 'The top of the head and back is bright green to olive in color; sides are yellowish-green to golden yellow with 6 to 8 dark vertical bands; belly area ranges from yellow to white; pectoral, pelvic and anal fins vary in color from pale yellow to bright orange.');",
		"INSERT INTO fish VALUES (9 , " + R.drawable.cusk + ", 'Cusk', 'Burbot, Lawyer Fish (Great Lake states), Freshwater Cod', 'Lota lota', 'Native', 'Sexual maturity is usually attained during the third or fourth year. Males often mature at a smaller size than females. Aged (otoliths) Maine cusk average 18 inches and 24 ounces in their eight year of growth, 20 inches and 32 ounces in their tenth year, and 24 inches and 62 ounces in their thirteenth year. The largest angler-caught cusk recorded in Maine was 18 pounds 8 ounces.', 'Cusk have an elongated body with a broad, flattened triangular head. The mouth is large and wide. A single chin barbel adorns the lower jaw. Scales are small and embedded giving burbot the appearance of being slimy. The anal and dorsal fins run nearly half the length of the body. In Maine, coloration ranges from a tan to dark brown background overlaid with dark brown to black mottling or spots. Some may be uniformly dark brown or black.');",
		"INSERT INTO fish VALUES (10 , " + R.drawable.brown_trout + ", 'Brown Trout', 'German Brown Trout, Brownie, Loch Leven Trout, Saibling', 'Salmo trutta', 'Introduced', 'Normal size is 14-20 inches and 1-2 pounds. Browns occasionally reach 10 pounds.', 'Usually coloration is light brown or tawny with pronounced black spots on the back, sides and head. Spots are often surrounded with reddish halo, along with reddish spots on the sides. Color is highly variable and browns are occasionally confused with landlocked salmon.');",
		"INSERT INTO fish VALUES (11 , " + R.drawable.northern_pike + ", 'Northern Pike', 'Pike, Northerns', 'Esox lucius', 'Introduced', 'Sexual maturity is generally reached between 3-5 years of age, at which time pike may average between 24-30 inches long and weigh 3.5 to 7.5 pounds. Pike exceeding 30 pounds have been caught in Maine.', 'Scales are present on the upper half of the gill cover, but are absent on the lower half. The cheek area (located just forward of the gill plate), is fully scaled. Pike usually have five pairs of sensory pores along the underside of the lower jaw. The cheeks and gill covers of chain pickerel are fully scaled, and generally only four pairs of sensor pores are present on the lower jaw. The pattern of markings is typically very different on adult and juvenile pike. Juvenile pike possess wavy, white to yellow vertical bars. Adults have shorter markings arranged in a more horizontal configuration. Pike can hybridize with chain pickerel, and the resulting hybrid may possess markings common to either or both species. Know the difference between Northern Pike and Chain Pickerel.');",
		"INSERT INTO fish VALUES (12 , " + R.drawable.smallmouth_bass + ", 'Smallmouth Bass', 'Smallie, Smallmouth Black Bass, Black Bass, Brown Bass, Green Bass', 'Micropterus dolomieu', 'Introduced', 'Smallmouth bass commonly live 5-7 years, with a few individuals reaching ages from 10-20 years. Most bass waters grow some adults to weights of 2-3 pounds. In Maine, only a few exceed 4 pounds, and bass heavier than 5 pounds are rare. The state record smallmouth bass is 8 pounds.', 'Bass are members of the sunfish family. The upper jaw of smallmouth bass does not extend beyond the back of the eye. The notch between the spiny and the soft-rayed section of the dorsal fin is not deep.');",
		"INSERT INTO fish VALUES (13 , " + R.drawable.white_perch + ", 'White Bass', 'Perch, Silver Perch', 'Morone americana', 'Native', 'Size can vary greatly according to the type of habitat and the density of the perch population. A 6-year old perch can be anywhere from 6 to 12 inches long. The average 8 to 10 inch perch weighs about 0.45 pounds and is about 4 years old.', 'The white perch is a spiny-finned fish with large, easily seen scales. The fish is dark gray-green on the back and upper sides and the color gradually changes to silver on the sides below the lateral line to white on the belly. In clear waters, the white perch exhibits a bluish tint on the lower jaw.');",
		"INSERT INTO fish VALUES (14 , " + R.drawable.whitefish + ", 'Lake Whitefish', 'Whitefish', 'Coregonus clupeaformis', 'Native', 'Whitefish are normally 14-20 inches long and weigh 1-3 pounds, but can reach lengths of 25 inches and over 6 pounds. A number of lakes contain populations of ''dwarf'' size fish where mature adult whitefish attain lengths of only 6-8 inches.', 'Overall coloration is silvery dark brown to black on the back, becoming silvery on the sides and silvery white below. The fins are generally black or black tipped. Scales are large. The body is somewhat laterally compressed. Lake whitefish are easily distinguished from fallfish (chub) by having a small mouth and the presence of a small adipose fin located on the back between the dorsal and caudal fins. A related species, the round whitefish is light brown on the back becoming silvery on the side. It has a more cylindrical body shape than the lake whitefish.');",
		"INSERT INTO fish VALUES (15 , " + R.drawable.splake + ", 'Splake', 'Wendigo', 'Salvelinus namaycush X Salvelinus fontinalis', 'Hatchery Hybrid', 'In Maine, splake typically range in size from 10 to 18 inches. Splake grow at a faster rate than either of its parental species. They can attain lengths of 18 inches in just 2 years after stocking. Splake over 10 pounds have been caught.', 'Splake and brook trout have very similar coloration patterns, making it very difficult for the untrained eye to distinguish between the two species. Splake tend to have a slight fork in the tail, a trait passed down from its lake trout parent, while brook trout tend to have no fork or ''square'' tails.');",
		"INSERT INTO fish VALUES (16 , " + R.drawable.largemouth_bass + ", 'Largemouth Bass', 'Black Bass, Largemouth', 'Micropterus salmoides', 'Introduced', 'The typical largemouth bass in Maine is 12-16 inches long and weigh 1-3 pounds, with occasional fish up to 7-8 pounds.', ' Largemouths are dark olive green on the back with light green sides shading to a white belly. A dark mottled band extends along the sides. The upper part of the mouth extends past the eye. Smallmouth bass are similar in appearance, but the upper jaw ends below the eye.');",
			"INSERT INTO fish VALUES (17 , " + R.drawable.bass_spotted + ", 'Spotted Bass', 'Black Bass, Largemouth', 'Micropterus punctulatus', 'Introduced', 'M. punctulatus can reach an overall length of almost 64 cm (25 in), reaching weights of up to 4.6 kg (10 lb). It can reach an age of at least seven years. It is noted for the rows of dark spots below the lateral line, which give it its common name', ' A convenient way to distinguish between a largemouth bass and a spotted bass is by the size of the mouth. A spotted bass will resemble a largemouth bass in coloration but will have a smaller mouth.');",
		};

	
	public static final String [] INSERT_DATA_lakesFish = new String [] {
			"INSERT INTO lakesFish values( 1 , 1, 17);",
			"INSERT INTO lakesFish values( 2 , 1, 2);", 
			"INSERT INTO lakesFish values( 3 , 1, 13);",
			"INSERT INTO lakesFish values( 4 , 2, 2);", 
			"INSERT INTO lakesFish values( 5 , 3, 1);", 
			"INSERT INTO lakesFish values( 6 , 3, 2);", 
			"INSERT INTO lakesFish values( 7 , 3, 3);", 
			"INSERT INTO lakesFish values( 8 , 3, 4);", 
			"INSERT INTO lakesFish values( 9 , 3, 5);", 
			"INSERT INTO lakesFish values( 10 , 4, 2);", 
			"INSERT INTO lakesFish values( 11 , 5, 2);", 
			"INSERT INTO lakesFish values( 12 , 5, 3);", 
			"INSERT INTO lakesFish values( 13 , 6, 1);", 
			"INSERT INTO lakesFish values( 14 , 6, 2);", 
			"INSERT INTO lakesFish values( 15 , 6, 3);", 
			"INSERT INTO lakesFish values( 16 , 7, 2);", 
			"INSERT INTO lakesFish values( 17 , 7, 3);", 
			"INSERT INTO lakesFish values( 18 , 8, 2);", 
			"INSERT INTO lakesFish values( 19 , 9, 2);", 
			"INSERT INTO lakesFish values( 20 , 10, 2);", 
			"INSERT INTO lakesFish values( 21 , 11, 2);", 
			"INSERT INTO lakesFish values( 22 , 8, 5);",
			"INSERT INTO lakesFish values( 23 , 12, 2);", 
			"INSERT INTO lakesFish values( 24 , 13, 2);", 
			"INSERT INTO lakesFish values( 25 , 14, 2);", 
			"INSERT INTO lakesFish values( 26 , 14, 5);", 
			"INSERT INTO lakesFish values( 27 , 15, 2);", 
			"INSERT INTO lakesFish values( 28 , 16, 2);", 
			"INSERT INTO lakesFish values( 29 , 16, 3);", 
			"INSERT INTO lakesFish values( 30 , 16, 5);", 
			"INSERT INTO lakesFish values( 31 , 17, 1);", 
			"INSERT INTO lakesFish values( 32 , 17, 2);", 
			"INSERT INTO lakesFish values( 33 , 8, 7);",
			"INSERT INTO lakesFish values( 34 , 17, 3);", 
			"INSERT INTO lakesFish values( 35 , 17, 8);", 
			"INSERT INTO lakesFish values( 36 , 8, 9);",
			"INSERT INTO lakesFish values( 37 , 18, 2);", 
			"INSERT INTO lakesFish values( 38 , 18, 8);", 
			"INSERT INTO lakesFish values( 39 , 19, 2);", 
			"INSERT INTO lakesFish values( 40 , 19, 8);", 
			"INSERT INTO lakesFish values( 41 , 19, 5);", 
			"INSERT INTO lakesFish values( 42 , 20, 2);", 
			"INSERT INTO lakesFish values( 43 , 20, 3);", 
			"INSERT INTO lakesFish values( 44 , 21, 2);", 
			"INSERT INTO lakesFish values( 45 , 22, 1);", 
			"INSERT INTO lakesFish values( 46 , 22, 2);", 
			"INSERT INTO lakesFish values( 47 , 22, 10);", 
			"INSERT INTO lakesFish values( 48 , 22, 3);", 
			"INSERT INTO lakesFish values( 49 , 22, 8);", 
			"INSERT INTO lakesFish values( 50 , 22, 5);", 
			"INSERT INTO lakesFish values( 51 , 23, 2);", 
			"INSERT INTO lakesFish values( 52 , 24, 2);", 
			"INSERT INTO lakesFish values( 53 , 25, 1);", 
			"INSERT INTO lakesFish values( 54 , 25, 2);", 
			"INSERT INTO lakesFish values( 55 , 25, 3);", 
			"INSERT INTO lakesFish values( 56 , 25, 8);", 
			"INSERT INTO lakesFish values( 57 , 26, 2);", 
			"INSERT INTO lakesFish values( 58 , 27, 2);", 
			"INSERT INTO lakesFish values( 59 , 27, 3);", 
			"INSERT INTO lakesFish values( 60 , 28, 2);", 
			"INSERT INTO lakesFish values( 61 , 28, 3);", 
			"INSERT INTO lakesFish values( 62 , 29, 2);", 
			"INSERT INTO lakesFish values( 63 , 30, 12);", 
			"INSERT INTO lakesFish values( 64 , 30, 13);", 
			"INSERT INTO lakesFish values( 65 , 30, 8);", 
			"INSERT INTO lakesFish values( 66 , 30, 4);", 
			"INSERT INTO lakesFish values( 67 , 30, 5);", 
			"INSERT INTO lakesFish values( 68 , 31, 1);", 
			"INSERT INTO lakesFish values( 69 , 31, 2);", 
			"INSERT INTO lakesFish values( 70 , 31, 3);", 
			"INSERT INTO lakesFish values( 71 , 31, 8);", 
			"INSERT INTO lakesFish values( 72 , 31, 4);", 
			"INSERT INTO lakesFish values( 73 , 31, 5);", 
			"INSERT INTO lakesFish values( 74 , 32, 2);", 
			"INSERT INTO lakesFish values( 75 , 32, 3);", 
			"INSERT INTO lakesFish values( 76 , 33, 2);", 
			"INSERT INTO lakesFish values( 77 , 33, 5);", 
			"INSERT INTO lakesFish values( 78 , 34, 2);", 
			"INSERT INTO lakesFish values( 79 , 35, 10);", 
			"INSERT INTO lakesFish values( 80 , 35, 2);", 
			"INSERT INTO lakesFish values( 81 , 35, 3);", 
			"INSERT INTO lakesFish values( 82 , 36, 2);", 
			"INSERT INTO lakesFish values( 83 , 36, 8);", 
			"INSERT INTO lakesFish values( 84 , 36, 5);", 
			"INSERT INTO lakesFish values( 85 , 37, 2);", 
			"INSERT INTO lakesFish values( 86 , 38, 1);", 
			"INSERT INTO lakesFish values( 87 , 38, 2);", 
			"INSERT INTO lakesFish values( 88 , 38, 3);", 
			"INSERT INTO lakesFish values( 89 , 38, 8);", 
			"INSERT INTO lakesFish values( 90 , 38, 5);", 
			"INSERT INTO lakesFish values( 91 , 39, 1);", 
			"INSERT INTO lakesFish values( 92 , 39, 2);", 
			"INSERT INTO lakesFish values( 93 , 39, 3);", 
			"INSERT INTO lakesFish values( 94 , 39, 8);", 
			"INSERT INTO lakesFish values( 95 , 39, 5);", 
			"INSERT INTO lakesFish values( 96 , 40, 2);", 
			"INSERT INTO lakesFish values( 97 , 40, 5);", 
			"INSERT INTO lakesFish values( 98 , 41, 1);", 
			"INSERT INTO lakesFish values( 99 , 41, 7);", 
			"INSERT INTO lakesFish values( 100 , 41, 2);", 
			"INSERT INTO lakesFish values( 101 , 41, 14);", 
			"INSERT INTO lakesFish values( 102 , 41, 3);", 
			"INSERT INTO lakesFish values( 103 , 42, 1);", 
			"INSERT INTO lakesFish values( 104 , 42, 2);", 
			"INSERT INTO lakesFish values( 105 , 42, 8);", 
			"INSERT INTO lakesFish values( 106 , 43, 2);", 
			"INSERT INTO lakesFish values( 107 , 44, 1);", 
			"INSERT INTO lakesFish values( 108 , 44, 2);", 
			"INSERT INTO lakesFish values( 109 , 44, 10);", 
			"INSERT INTO lakesFish values( 110 , 44, 3);", 
			"INSERT INTO lakesFish values( 111 , 45, 2);", 
			"INSERT INTO lakesFish values( 112 , 46, 2);", 
			"INSERT INTO lakesFish values( 113 , 47, 2);", 
			"INSERT INTO lakesFish values( 114 , 47, 7);", 
			"INSERT INTO lakesFish values( 115 , 47, 3);", 
			"INSERT INTO lakesFish values( 116 , 48, 2);", 
			"INSERT INTO lakesFish values( 117 , 48, 3);", 
			"INSERT INTO lakesFish values( 118 , 49, 1);", 
			"INSERT INTO lakesFish values( 119 , 49, 2);", 
			"INSERT INTO lakesFish values( 120 , 49, 3);", 
			"INSERT INTO lakesFish values( 121 , 50, 2);", 
			"INSERT INTO lakesFish values( 122 , 51, 2);", 
			"INSERT INTO lakesFish values( 123 , 52, 2);", 
			"INSERT INTO lakesFish values( 124 , 53, 7);", 
			"INSERT INTO lakesFish values( 125 , 53, 2);", 
			"INSERT INTO lakesFish values( 126 , 54, 1);", 
			"INSERT INTO lakesFish values( 127 , 54, 2);", 
			"INSERT INTO lakesFish values( 128 , 54, 3);", 
			"INSERT INTO lakesFish values( 129 , 55, 2);", 
			"INSERT INTO lakesFish values( 130 , 56, 2);", 
			"INSERT INTO lakesFish values( 131 , 57, 2);", 
			"INSERT INTO lakesFish values( 132 , 58, 2);", 
			"INSERT INTO lakesFish values( 133 , 58, 3);", 
			"INSERT INTO lakesFish values( 134 , 59, 2);", 
			"INSERT INTO lakesFish values( 135 , 60, 1);", 
			"INSERT INTO lakesFish values( 136 , 60, 2);", 
			"INSERT INTO lakesFish values( 137 , 60, 3);", 
			"INSERT INTO lakesFish values( 138 , 61, 2);", 
			"INSERT INTO lakesFish values( 139 , 61, 10);", 
			"INSERT INTO lakesFish values( 140 , 61, 15);", 
			"INSERT INTO lakesFish values( 141 , 61, 3);", 
			"INSERT INTO lakesFish values( 142 , 61, 12);", 
			"INSERT INTO lakesFish values( 143 , 61, 5);", 
			"INSERT INTO lakesFish values( 144 , 61, 13);", 
			"INSERT INTO lakesFish values( 145 , 62, 2);", 
			"INSERT INTO lakesFish values( 146 , 63, 1);", 
			"INSERT INTO lakesFish values( 147 , 63, 2);", 
			"INSERT INTO lakesFish values( 148 , 63, 10);", 
			"INSERT INTO lakesFish values( 149 , 63, 3);", 
			"INSERT INTO lakesFish values( 150 , 63, 8);", 
			"INSERT INTO lakesFish values( 151 , 63, 5);", 
			"INSERT INTO lakesFish values( 152 , 64, 2);", 
			"INSERT INTO lakesFish values( 153 , 64, 10);", 
			"INSERT INTO lakesFish values( 154 , 64, 15);", 
			"INSERT INTO lakesFish values( 155 , 64, 3);", 
			"INSERT INTO lakesFish values( 156 , 64, 12);", 
			"INSERT INTO lakesFish values( 157 , 64, 5);", 
			"INSERT INTO lakesFish values( 158 , 64, 13);", 
			"INSERT INTO lakesFish values( 159 , 65, 2);", 
			"INSERT INTO lakesFish values( 160 , 66, 2);", 
			"INSERT INTO lakesFish values( 161 , 67, 1);", 
			"INSERT INTO lakesFish values( 162 , 67, 2);", 
			"INSERT INTO lakesFish values( 163 , 68, 2);", 
			"INSERT INTO lakesFish values( 164 , 68, 5);", 
			"INSERT INTO lakesFish values( 165 , 69, 2);", 
			"INSERT INTO lakesFish values( 166 , 70, 2);", 
			"INSERT INTO lakesFish values( 167 , 71, 2);", 
			"INSERT INTO lakesFish values( 168 , 72, 1);", 
			"INSERT INTO lakesFish values( 169 , 72, 2);", 
			"INSERT INTO lakesFish values( 170 , 72, 3);", 
			"INSERT INTO lakesFish values( 171 , 73, 2);", 
			"INSERT INTO lakesFish values( 172 , 73, 8);", 
			"INSERT INTO lakesFish values( 173 , 74, 2);", 
			"INSERT INTO lakesFish values( 174 , 75, 2);", 
			"INSERT INTO lakesFish values( 175 , 75, 5);", 
			"INSERT INTO lakesFish values( 176 , 76, 1);", 
			"INSERT INTO lakesFish values( 177 , 76, 7);", 
			"INSERT INTO lakesFish values( 178 , 76, 2);", 
			"INSERT INTO lakesFish values( 179 , 76, 3);", 
			"INSERT INTO lakesFish values( 180 , 76, 8);", 
			"INSERT INTO lakesFish values( 181 , 76, 4);", 
			"INSERT INTO lakesFish values( 182 , 76, 5);", 
			"INSERT INTO lakesFish values( 183 , 77, 2);", 
			"INSERT INTO lakesFish values( 184 , 78, 2);", 
			"INSERT INTO lakesFish values( 185 , 78, 3);", 
			"INSERT INTO lakesFish values( 186 , 79, 1);", 
			"INSERT INTO lakesFish values( 187 , 79, 2);", 
			"INSERT INTO lakesFish values( 188 , 79, 3);", 
			"INSERT INTO lakesFish values( 189 , 79, 8);", 
			"INSERT INTO lakesFish values( 190 , 79, 5);", 
			"INSERT INTO lakesFish values( 191 , 80, 2);", 
			"INSERT INTO lakesFish values( 192 , 81, 2);", 
			"INSERT INTO lakesFish values( 193 , 82, 1);", 
			"INSERT INTO lakesFish values( 194 , 82, 7);", 
			"INSERT INTO lakesFish values( 195 , 82, 2);", 
			"INSERT INTO lakesFish values( 196 , 82, 3);", 
			"INSERT INTO lakesFish values( 197 , 82, 8);", 
			"INSERT INTO lakesFish values( 198 , 82, 5);", 
			"INSERT INTO lakesFish values( 199 , 83, 1);", 
			"INSERT INTO lakesFish values( 200 , 83, 2);", 
			"INSERT INTO lakesFish values( 201 , 83, 3);", 
			"INSERT INTO lakesFish values( 202 , 83, 8);", 
			"INSERT INTO lakesFish values( 203 , 83, 5);", 
			"INSERT INTO lakesFish values( 204 , 84, 2);", 
			"INSERT INTO lakesFish values( 205 , 85, 2);", 
			"INSERT INTO lakesFish values( 206 , 86, 2);", 
			"INSERT INTO lakesFish values( 207 , 86, 5);", 
			"INSERT INTO lakesFish values( 208 , 87, 2);", 
			"INSERT INTO lakesFish values( 209 , 88, 1);", 
			"INSERT INTO lakesFish values( 210 , 88, 2);", 
			"INSERT INTO lakesFish values( 211 , 88, 3);", 
			"INSERT INTO lakesFish values( 212 , 88, 8);", 
			"INSERT INTO lakesFish values( 213 , 89, 2);", 
			"INSERT INTO lakesFish values( 214 , 90, 2);", 
			"INSERT INTO lakesFish values( 215 , 91, 2);", 
			"INSERT INTO lakesFish values( 216 , 91, 3);", 
			"INSERT INTO lakesFish values( 217 , 92, 2);", 
			"INSERT INTO lakesFish values( 218 , 92, 4);", 
			"INSERT INTO lakesFish values( 219 , 93, 2);", 
			"INSERT INTO lakesFish values( 220 , 94, 2);", 
			"INSERT INTO lakesFish values( 221 , 94, 3);", 
			"INSERT INTO lakesFish values( 222 , 94, 5);", 
			"INSERT INTO lakesFish values( 223 , 95, 2);", 
			"INSERT INTO lakesFish values( 224 , 95, 3);", 
			"INSERT INTO lakesFish values( 225 , 95, 5);", 
			"INSERT INTO lakesFish values( 226 , 96, 2);", 
			"INSERT INTO lakesFish values( 227 , 96, 3);", 
			"INSERT INTO lakesFish values( 228 , 96, 5);", 
			"INSERT INTO lakesFish values( 229 , 97, 2);", 
			"INSERT INTO lakesFish values( 230 , 98, 2);", 
			"INSERT INTO lakesFish values( 231 , 99, 8);", 
			"INSERT INTO lakesFish values( 232 , 100, 2);", 
			"INSERT INTO lakesFish values( 233 , 101, 2);", 
			"INSERT INTO lakesFish values( 234 , 102, 2);", 
			"INSERT INTO lakesFish values( 235 , 103, 2);", 
			"INSERT INTO lakesFish values( 236 , 103, 3);", 
			"INSERT INTO lakesFish values( 237 , 104, 2);", 
			"INSERT INTO lakesFish values( 238 , 105, 1);", 
			"INSERT INTO lakesFish values( 239 , 105, 2);", 
			"INSERT INTO lakesFish values( 240 , 105, 15);", 
			"INSERT INTO lakesFish values( 241 , 105, 3);", 
			"INSERT INTO lakesFish values( 242 , 105, 8);", 
			"INSERT INTO lakesFish values( 243 , 105, 5);", 
			"INSERT INTO lakesFish values( 244 , 106, 2);", 
			"INSERT INTO lakesFish values( 245 , 106, 3);", 
			"INSERT INTO lakesFish values( 246 , 107, 2);", 
			"INSERT INTO lakesFish values( 247 , 108, 1);", 
			"INSERT INTO lakesFish values( 248 , 108, 7);", 
			"INSERT INTO lakesFish values( 249 , 108, 2);", 
			"INSERT INTO lakesFish values( 250 , 108, 14);", 
			"INSERT INTO lakesFish values( 251 , 108, 3);", 
			"INSERT INTO lakesFish values( 252 , 109, 2);", 
			"INSERT INTO lakesFish values( 253 , 110, 2);", 
			"INSERT INTO lakesFish values( 254 , 110, 3);", 
			"INSERT INTO lakesFish values( 255 , 111, 2);", 
			"INSERT INTO lakesFish values( 256 , 111, 10);", 
			"INSERT INTO lakesFish values( 257 , 111, 15);", 
			"INSERT INTO lakesFish values( 258 , 111, 3);", 
			"INSERT INTO lakesFish values( 259 , 112, 2);", 
			"INSERT INTO lakesFish values( 260 , 113, 1);", 
			"INSERT INTO lakesFish values( 261 , 113, 7);", 
			"INSERT INTO lakesFish values( 262 , 113, 2);", 
			"INSERT INTO lakesFish values( 263 , 113, 3);", 
			"INSERT INTO lakesFish values( 264 , 113, 12);", 
			"INSERT INTO lakesFish values( 265 , 113, 6);", 
			"INSERT INTO lakesFish values( 266 , 113, 8);", 
			"INSERT INTO lakesFish values( 267 , 113, 11);", 
			"INSERT INTO lakesFish values( 268 , 113, 4);", 
			"INSERT INTO lakesFish values( 269 , 113, 5);", 
			"INSERT INTO lakesFish values( 270 , 114, 1);", 
			"INSERT INTO lakesFish values( 271 , 114, 10);", 
			"INSERT INTO lakesFish values( 272 , 114, 2);", 
			"INSERT INTO lakesFish values( 273 , 114, 3);", 
			"INSERT INTO lakesFish values( 274 , 114, 12);", 
			"INSERT INTO lakesFish values( 275 , 114, 13);", 
			"INSERT INTO lakesFish values( 276 , 114, 8);", 
			"INSERT INTO lakesFish values( 277 , 114, 4);", 
			"INSERT INTO lakesFish values( 278 , 114, 5);", 
			"INSERT INTO lakesFish values( 279 , 115, 2);", 
			"INSERT INTO lakesFish values( 280 , 116, 2);", 
			"INSERT INTO lakesFish values( 281 , 116, 4);", 
			};

	

	AnglerDbAdapter(Context context) {
		this.ADbAContext = context;
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		public OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			//Create Tables
			db.execSQL(CREATE_TABLE_lakes);
			db.execSQL(CREATE_TABLE_fish);
			db.execSQL(CREATE_TABLE_lakesFish);

			//Insert Data Into Tables
			multiStatement(INSERT_DATA_lakes, db);
			multiStatement(INSERT_DATA_fish, db);
			multiStatement(INSERT_DATA_lakesFish, db);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS lakes");
            db.execSQL("DROP TABLE IF EXISTS fish");
            db.execSQL("DROP TABLE IF EXISTS lakesFish");
			onCreate(db);
		}

		public void multiStatement(String[] s, SQLiteDatabase MSdatabase) {
			for (int i = 0; i < s.length; i++) {
				MSdatabase.execSQL(s[i]);
			}
		}
	}

	public AnglerDbAdapter open() throws SQLException {
		DbHelper = new OpenHelper(ADbAContext);
		database = DbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		database.close();
	}
	
	public Cursor getAllLakes() {
		String select = "SELECT _id, name FROM lakes";
		Cursor cursor = database.rawQuery(select, new String[] {});
		return cursor;
	}
	
	public Cursor getAllFish() {
		String select = "SELECT _id, name FROM fish ORDER BY name";
		Cursor cursor = database.rawQuery(select, new String[] {});
		return cursor;
	}
	
	public Cursor getLake(int id) {
		id++;
		String select = "SELECT * FROM lakes where _id = " + id;
		Cursor cursor = database.rawQuery(select, new String[] {});
		return cursor;
	}
	
	public Cursor getFish(int id) {
		String select = "SELECT * FROM fish where _id = " + id + " ORDER BY name";
		Cursor cursor = database.rawQuery(select, new String[] {});
		return cursor;
	}
	
	public Cursor getFishFromLake(int id) {
		String select = "SELECT _id,name,fish_resID FROM fish where _id in (SELECT fishid from lakesfish where lakeid = " + id + ") ORDER BY name";
		Cursor cursor = database.rawQuery(select, new String[] {});
		return cursor;
	}
	
	public Cursor getLakesFromFish(int id) {
		String select = "SELECT _id,name,depthImage_resID FROM lakes where _id in (SELECT lakeid from lakesfish where fishid = " + id + ")";
		Cursor cursor = database.rawQuery(select, new String[] {});
		return cursor;
	}
	
	
	
	
}
