mcs search verifier
mcs class-search EqualsVerifier
mcs search nl.jqno.equalsverifier:equalsverifier:1.4.1

jshell --class-path .
# Use tab completion

# int value = 2

# Math.random * value

# System.out.println($2)

# double times10(double input) {
#  return input*10;
# }

# times10($2)

# /vars
# /methods
# /list
# /edit 1

# /exit

jbang init ThisIsTheWay.java
jbang ThisIsTheWay.java

jbang init -t cli ExampleCLI.java
jbang ExampleCLI.java hellow
# Add dependencies after /DEPS in the ExampleCLI.java file: com.googlecode.libphonenumber:libphonenumber:8.12.46

# Add:

# import com.google.i18n.phonenumbers.PhoneNumberUtil;
# import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

# private PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

# System.out.println("Hello " + greeting);
# PhoneNumber parsedNumber = phoneNumberUtil.parse(greeting, "NL");
# System.out.println("Phone number is valid: " + phoneNumberUtil.isValidNumber(parsedNumber));

# jbang ExampleCLI.java hellow
# jbang ExampleCLI.java 0612345678

# JLink create custom Java runtime image
cd Examples\src\main\java\
javac -d . com\examples\jlink\HelloJlink.java
jdeps -s com\examples\jlink\HelloJlink.class

jlink --add-modules java.base,java.logging --output jlink-image --launcher launch=com.examples.jlink.HelloJlink
jlink-image\bin\java.exe com.examples.jlink.HelloJlink

# JLink with modules and launcher
# Doesn't work? javac -d out com\examples\jlink\module-info.java

javac -d out --module-path out com\examples\jlink\HelloJlink.java
java --module-path out --module jlinkModule/com.examples.jlink.HelloJlink
jlink --launcher customjrelauncher=jlinkModule/com.examples.jlink.HelloJlink --module-path "%JAVA_HOME%\jmods";out --add-modules jlinkModule --output jpackagecustumjre
jpackagecustumjre\bin\customjrelauncher.bat



# JPackage self contained Java package
javac -d . com\examples\jpackage\Hellojpackage.java
jar cf jpackageExample.jar com/examples/jpackage/*.class

# Needs wix tools
jpackage --input target/ --name jpackageExample --main-jar jpackageExample.jar --main-class com.examples.jlink.HelloJlink --type msi

# module-info.java in the com/examples/jlink directory gives issues, removed it for now
# module jlinkModule
#  {
#      requires java.logging;
#  }

mvn test -Dmaven.test.skip
mvnw test -Dmaven.test.skip
mvnd test -Dmaven.test.skip

jmc

mvn jreleaser:assemble
mvn jreleaser:changelog