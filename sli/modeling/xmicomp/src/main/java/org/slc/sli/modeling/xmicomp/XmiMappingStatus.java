package org.slc.sli.modeling.xmicomp;public enum XmiMappingStatus {    /**     * transient     */    TRANSIENT("transient"),    /**     * unknown     */    UNKNOWN("unknown"),    /**     * match     */    MATCH("match"),    /**     * ignorable     */    IGNORABLE("ignorable");    private final String name;        XmiMappingStatus(final String name) {        this.name = name;    }        public String getName() {        return name;    }        public static XmiMappingStatus valueOfName(final String name) {        for (final XmiMappingStatus value : values()) {            if (value.getName().equals(name)) {                return value;            }        }        return null;    }}