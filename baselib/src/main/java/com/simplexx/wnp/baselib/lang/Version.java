package com.simplexx.wnp.baselib.lang;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class Version implements Comparable<Version> {

    private Integer major;
    private Integer minor;
    private Integer build;
    private Integer revision;

    /**
     * @param major    主版本号
     * @param minor    次版本号
     * @param build    内部版本号
     * @param revision 修订号
     */
    public Version(int major, int minor, int build, int revision) {
        init(major, minor, build, revision);
    }

    public Version(String versionName) {
        int[] vs = {0, 0, 0, 0};
        if (versionName != null) {
            versionName=versionName.replaceAll(".beta","");
            String[] arr = versionName.split("\\.");
            for (int i = 0; i < arr.length && i < vs.length; i++) {
                int value = 0;
                try {
                    value = Integer.parseInt(arr[i]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                vs[i] = value;
            }
        }
        init(vs[0], vs[1], vs[2], vs[3]);
    }

    private void init(int major, int minor, int build, int revision) {
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.revision = revision;
    }

    @Override
    public int compareTo(Version version) {
        if (version == null)
            return 1;
        int result = major.compareTo(version.major);
        if (result == 0)
            result = minor.compareTo(version.minor);
        if (result == 0)
            result = build.compareTo(version.build);
        if (result == 0)
            result = revision.compareTo(version.revision);
        return result;
    }


    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRevision() {
        return revision;
    }

    public int getBuild() {
        return build;
    }

    @Override
    public String toString() {
        return major + "." + minor + "." + build + "." + revision;
    }
}

