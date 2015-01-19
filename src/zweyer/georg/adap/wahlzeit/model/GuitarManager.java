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

public class GuitarManager extends ObjectManager{

	/**
	 * 
	 */
	protected static final GuitarManager instance = new GuitarManager();

	/* Collaboration: 
	 * Manager
	 */
	/**
	 * In-memory cache for guitars
	 */
	protected Map<Integer, Guitar> guitarCache = new HashMap<Integer, Guitar>();
	protected int currentId = 0;
	//-------------
	
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
	public static final GuitarManager getInstance() {
		return instance;
	}
	/**
	 * @methodtype constructor
	 */
	protected GuitarManager() {
	}
	
	/* Collaboration: 
	 * Manager
	 */
	public final boolean hasGuitar(Integer id) {
		try {
			getGuitarFromId(id);
		} catch (GuitarNotFoundException e) {
			return false;
		}
		return true;
	}
	protected boolean doHasGuitar(Integer id) {
		return this.guitarCache.containsKey(id);
	}
	public final Guitar getGuitarFromId(Integer id) throws GuitarNotFoundException {
		if (id == null || id < -1) {
			throw new IllegalArgumentException();
		}
		Guitar result = this.doGetGuitarFromId(id);
		if (result == null) {
			try {
				PreparedStatement stmt = getReadingStatement("SELECT * FROM guitars WHERE id = ?");
				result = (Guitar) readObject(stmt, id.intValue());
			} catch (SQLException sex) {
				SysLog.logThrowable(sex);
			}
			if (result != null) {
				doAddGuitar(result);
			}
		}
		if(result == null){
			throw new GuitarNotFoundException();
		}
		return result;
	}
	protected Guitar doGetGuitarFromId(Integer id) {
		return this.guitarCache.get(id);
	}
	public void addGuitar(Guitar guitar){
		assertIsNewGuitar(guitar.getId());
		doAddGuitar(guitar);

		try {
			PreparedStatement stmt = getReadingStatement("INSERT INTO guitars(id) VALUES(?)");
			createObject(guitar, stmt, guitar.getId());
			saveGuitar(guitar);
			ServiceMain.getInstance().saveGlobals();
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	protected void doAddGuitar(Guitar guitar) {
		this.guitarCache.put(guitar.getId(), guitar);
	}
	public Guitar createGuitar() throws UnableToCreateGuitarException {
		int noOfTries = 0;
		while (noOfTries < 3) {
			this.currentId++;
			Integer id = Integer.valueOf(this.currentId);
			Guitar result = GuitarFactory.getInstance().createGuitar(id);
			try {
				addGuitar(result);
				return result;
			} catch (IllegalStateException ise) {
				SysLog.logThrowable(ise);
				noOfTries++;
			}
		}
		throw new UnableToCreateGuitarException();
	}
	public void saveGuitar(Guitar guitar) {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM guitars WHERE id = ?");
			updateObject(guitar, stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	public void saveGuitars() {
		try {
			PreparedStatement stmt = getUpdatingStatement("SELECT * FROM guitars WHERE id = ?");
			updateObjects(this.guitarCache.values(), stmt);
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
	}
	public Collection<Guitar> loadGuitars() {
		try {
			ArrayList<Guitar> list = new ArrayList<Guitar>();
			PreparedStatement stmt = getReadingStatement("SELECT * FROM guitars");
			readObjects(list, stmt);
			for (Iterator<Guitar> i = list.iterator(); i.hasNext(); ) {
				Guitar guitar = i.next();
				if (!doHasGuitar(guitar.getId())) {
					doAddGuitar(guitar);
				} else {
					SysLog.logSysInfo("guitar", guitar.getId().toString(), "guitar had already been loaded");
				}
			}
		} catch (SQLException sex) {
			SysLog.logThrowable(sex);
		}
		
		SysLog.logSysInfo("loaded all guitars");
		return guitarCache.values();
		
	}
	//-------------
	
	/* Collaboration: 
	 * Serializer
	 */
	@Override
	protected Persistent createObject(ResultSet rset) throws SQLException {
		return GuitarFactory.getInstance().createGuitar(rset);
	}
	//-------------
	
	private void assertIsNewGuitar(Integer id) {
		if (hasGuitar(id)) {
			throw new IllegalStateException("Guitar already exists!");
		}
	}
}
