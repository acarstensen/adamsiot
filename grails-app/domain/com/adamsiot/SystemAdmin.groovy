package com.adamsiot

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class SystemAdmin implements Serializable {

	private static final long serialVersionUID = 1

	System system
	Admin admin

	@Override
	boolean equals(other) {
		if (other instanceof SystemAdmin) {
			other.systemId == system?.id && other.adminId == admin?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (system) {
            hashCode = HashCodeHelper.updateHash(hashCode, system.id)
		}
		if (admin) {
		    hashCode = HashCodeHelper.updateHash(hashCode, admin.id)
		}
		hashCode
	}

	static SystemAdmin get(long systemId, long adminId) {
		criteriaFor(systemId, adminId).get()
	}

	static boolean exists(long systemId, long adminId) {
		criteriaFor(systemId, adminId).count()
	}

	private static DetachedCriteria criteriaFor(long systemId, long adminId) {
		SystemAdmin.where {
			system == System.load(systemId) &&
			admin == Admin.load(adminId)
		}
	}

	static SystemAdmin create(System system, Admin admin, boolean flush = false) {
		def instance = new SystemAdmin(system: system, admin: admin)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(System u, Admin r) {
		if (u != null && r != null) {
			SystemAdmin.where { system == u && admin == r }.deleteAll()
		}
	}

	static int removeAll(System u) {
		u == null ? 0 : SystemAdmin.where { system == u }.deleteAll() as int
	}

	static int removeAll(Admin r) {
		r == null ? 0 : SystemAdmin.where { admin == r }.deleteAll() as int
	}

	static constraints = {
	    system nullable: false
		admin nullable: false, validator: { Admin r, SystemAdmin ur ->
			if (ur.system?.id) {
				if (SystemAdmin.exists(ur.system.id, r.id)) {
				    return ['userRole.exists']
				}
			}
		}
	}

	static mapping = {
		id composite: ['system', 'admin']
		version false
	}
}
