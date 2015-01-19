package zweyer.georg.adap.wahlzeit.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.ObjectManager;
import org.wahlzeit.services.Persistent;
import org.wahlzeit.services.SysLog;

public class GuitarTypeManager extends ObjectManager {

	/**
	 * 
	 */
	protected static final GuitarTypeManager instance = new GuitarTypeManager();
	
	/* Collaboration: 
	 * Manager
	 */
	/**
	 * In-memory cache for guitartypes
	 */
	protected Map<Integer, GuitarType> guitarTypeCache = new HashMap<Integer, GuitarType>();
	protected int currentId = 0;
	//-------------------
	
	public void setCurrentId(int currentId) {
		if (currentId < 0) {
			throw new IllegalArgumentException();
		}
		
		this.currentId = currentId;
	}
	public int getCurrentId() {
		return currentId;
	}

	/**
	 * 
	 */
	public static final GuitarTypeManager getInstance() {
		return instance;
	}
	
	/**
	 * @methodtype constructor
	 */
	protected GuitarTypeManager() {
	}
	
	
	/* Collaboration: 
	 * Manager
	 */
	public final boolean hasGuitarType(Integer id) {
		try {
			getGuitarTypeFromId(id);
		} catch (GuitarTypeNotFoundException e) {
			return false;
		}
		return true;
	}
	protected boolean doHasGuitarType(Integer id) {
		return this.guitarTypeCache.containsKey(id);
	}
	public final GuitarType getGuitarTypeFromId(Integer id) throws GuitarTypeNotFoundException {
		if (id == null || id < -1) {
			throw new IllegalArgumentException();
		}
		GuitarType result = this.doGetGuitarTypeFromId(id);
		if (result == null) {
			try {
				PreparedStatement stmt = getReadingStatement("SELECT * FROM guitar_types WHERE id = ?");
				result = (GuitarType) readObject(stmt, id.intValue());
			} catch (SQLException sex) {
				SysLog.logThrowable(sex);
			}
			if (result != null) {
				doAddGuitarType(result);
			}
		}
		if(result == null){
			throw new GuitarTypeNotFoundException();
		}
		return result;
	}
	protected GuitarType doGetGuitarTypeFromId(Integer id) {
		return this.guitarTypeCache.get(id);
	}
	public void addGuitarType(GuitarType guitarType){
		assertIsNewGuitarType(guitarType.getId());
		doAddGuitarType(guitarType);

		try {
			PreparedStatement stmt = getReadingStatement("INSERT INTO guitar_types(id) VALUES(?)");
			createObject(guitarType, stmt, guitarType.getId());
			saveGuitarType(guitarType);
			ServiceMain.getInstance().saveGlobals();
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	protected void doAddGuitarType(GuitarType guitarType) {
		this.guitarTypeCache.put(guitarType.getId(), guitarType);
	}
	public GuitarType createGuitarType() throws UnableToCreateGuitarTypeException {
		int noOfTries = 0;
		while (noOfTries < 3) {
			this.currentId++;
			Integer id = Integer.valueOf(this.currentId);
			GuitarType result = GuitarTypeFactory.getInstance().createGuitarType(id);
		
			try {
				addGuitarType(result);
				return result;
			} catch (IllegalStateException ise) {
				SysLog.logThrowable(ise);
				noOfTries++;
			}
		}
		throw new UnableToCreateGuitarTypeException();
	}
	public void saveGuitarType(GuitarType guitarType) {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM guitar_types WHERE id = ?");
			updateObject(guitarType, stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	public void saveGuitarTypes() {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM guitar_types WHERE id = ?");
			updateObjects(this.guitarTypeCache.values(), stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	public Collection<GuitarType> loadGuitarTypes() {
		try {
			ArrayList<GuitarType> list = new ArrayList<GuitarType>();
			PreparedStatement stmt = getReadingStatement("SELECT * FROM guitar_types ");
			readObjects(list, stmt);
			for (Iterator<GuitarType> i = list.iterator(); i.hasNext(); ) {
				GuitarType guitarType = i.next();
				if (!doHasGuitarType(guitarType.getId())) {
					doAddGuitarType(guitarType);
				} else {
					SysLog.logSysInfo("guitarType", guitarType.getId().toString(), "guitarType had already been loaded");
				}
			}
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
		
		SysLog.logSysInfo("loaded all guitarTypes");
		return guitarTypeCache.values();
		
	}
	//---------------------
	
	/* Collaboration: 
	 * Serializer
	 */
	@Override
	protected Persistent createObject(ResultSet rset) throws SQLException {
		return GuitarTypeFactory.getInstance().createGuitarType(rset);
	}
	//---------------
	
	private void assertIsNewGuitarType(Integer id) {
		if (hasGuitarType(id)) {
			throw new IllegalStateException("GuitarType already exists!");
		}
	}
}
