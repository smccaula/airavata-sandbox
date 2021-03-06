package org.apache.airavata.credential.store.impl.db;

import org.apache.airavata.credential.store.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Super class to abstract out Data access classes.
 */
public class ParentDAO {
    protected static Logger log = LoggerFactory.getLogger(CommunityUserDAO.class);

    protected DBUtil dbUtil;

    public ParentDAO(DBUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

}
