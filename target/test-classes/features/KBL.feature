Feature: KBL1
#Regression Type
#Correct Values = true
#Incorrect Values = false
#Illegal Values = false
#Invalid Values = false
#Boundary Values = false
#Edge Cases Values = false

@Digital_Journey_for_Xpress_Cash_Loan
@uid1352345228
@semiauto
@test001
@caseid_01
Scenario Outline: Digital Journey for Xpress Cash Loan
Given I have access to application
Then verify text Karnataka Bank logo in home page
When I selected Existing bank Customer login in home page
And I checked requirements in home page
And I selected Proceed to create application in home page
And I entered Customer ID in existing bank customer as '<Customer ID>'
And I entered Captchaa1 in existing bank customer as '<Captchaa1>'
And I checked Terms and conditions in existing bank customer
And I selected Generate OTP1 in existing bank customer
And I entered ENTER OTP1 in existing bank customer as '<ENTER OTP>'
And I selected Verify OTP1 in existing bank customer

Then '<page>' is displayed with '<content>'

Examples:
|SlNo.|Customer ID|Captchaa1|ENTER OTP|Name as per PAN card|EMAIL ADDRESS|ENTER AADHAAR NUMBER|REENTER ID PROOF NUMBER|org or company name|COMPANY ADDRESS|PINCODE|NET MONTHLY SALARY|MONTHLY RENT|EXISTING EMIS IF ANY|Login Id|PassWord|Wait|Wait1|page|content|
|1|766062882|123456|123456|M BEENA|rajesh@gmail.com|999999999999|999999999999|MSO|No 72, KEONICS, Hosur Rd, Electronic City, Bengaluru, Karnataka 560100|560100|70000|20000|10000|123456|123456|3|100|NA|NA|


#Total No. of Test Cases : 1

@Digital_Journey_for_Xpress_Cash_Loan_failled
@uid1352345229
@semiauto
@test002
@caseid_02
Scenario Outline: Login to th application with OTP - Fail TC
Given I have access to application
Then verify text Karnataka Bank logo in home page
When I selected Existing bank Customer login in home page
And I checked requirements in home page
And I selected Proceed to create application in home page
And I entered Customer ID in existing bank customer as '<Customer ID>'
And I entered Captchaa1 in existing bank customer as '<Captchaa1>'
And I checked Terms and conditions in existing bank customer
And I selected Generate OTP1 in existing bank customer
And I entered ENTER OTP2 in existing bank customer as '<ENTER OTP>'
And I selected Verify OTP2 in existing bank customer

Then '<page>' is displayed with '<content>'

Examples:
|SlNo.|Customer ID|Captchaa1|ENTER OTP|Name as per PAN card|EMAIL ADDRESS|ENTER AADHAAR NUMBER|REENTER ID PROOF NUMBER|org or company name|COMPANY ADDRESS|PINCODE|NET MONTHLY SALARY|MONTHLY RENT|EXISTING EMIS IF ANY|Login Id|PassWord|Wait|Wait1|page|content|
|1|766062882|123456|123456|M BEENA|rajesh@gmail.com|999999999999|999999999999|MSO|No 72, KEONICS, Hosur Rd, Electronic City, Bengaluru, Karnataka 560100|560100|70000|20000|10000|123456|123456|3|100|NA|NA|


#Total No. of Test Cases : 2

