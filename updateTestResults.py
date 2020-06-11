import xml.etree.ElementTree as ET
mytree = ET.parse('ProvarProject/ANT/Results/Overview.html')
myroot = mytree.getroot()
numberOfFailedTests=myroot[1][6][1][1].text
if (int(numberOfFailedTests) > 0):
    print (numberOfFailedTests + ' Tests Failed.')
    raise Exception("The pipeline has failed due to at least 1 test case failing.")