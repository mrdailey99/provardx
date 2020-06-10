import xml.etree.ElementTree as ET
import os
mytree = ET.parse('ProvarProject/ANT/Results/Overview.html')
# mytree = ET.parse('Overview.html')
myroot = mytree.getroot()
numberOfFailedTests=myroot[1][6][1][1].text
print(numberOfFailedTests)
if (int(numberOfFailedTests) > 0):
    print (numberOfFailedTests + ' Tests Failed')
    os.environ['AGENT_JOBSTATUS'] = 'Failed'
    print(os.environ['AGENT_JOBSTATUS'])