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