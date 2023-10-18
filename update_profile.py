import xml.etree.ElementTree as ET
import sys

# Ensure correct arguments are provided
if len(sys.argv) < 3 or len(sys.argv) > 4:
    print("Usage: python update_profile.py <PROFILE_FILE_NAME.profile-meta.xml> [<start_ip>] [<end_ip>]")
    sys.exit(1)

filename = sys.argv[1]
start_ip = sys.argv[2]
# Check if end_ip is provided, otherwise use start_ip
end_ip = sys.argv[3] if len(sys.argv) == 4 else start_ip

# 1. Parse the XML.
tree = ET.parse(filename)
root = tree.getroot()

changed = False

# Check for existing loginIpRanges with the description 'Azure Agent IP' to replace
existing_ranges = [r for r in root.findall('loginIpRanges') 
                   if (desc := r.find('description')) is not None and 
                   desc.text and desc.text == 'Azure Agent IP']

# If such ranges exist, remove them
for r in existing_ranges:
    root.remove(r)
    changed = True

# Create new loginIpRanges entry
ip_ranges = ET.SubElement(root, 'loginIpRanges')
ET.SubElement(ip_ranges, 'startAddress').text = start_ip
ET.SubElement(ip_ranges, 'endAddress').text = end_ip
ET.SubElement(ip_ranges, 'description').text = 'Azure Agent IP'
changed = True

# Serialize the XML and write to file if changes were made
if changed:
    tree.write(filename, xml_declaration=True, encoding='utf-8', method="xml")
    print(f"IP range {start_ip} to {end_ip} with description 'Azure Agent IP' added/updated successfully in {filename}!")
else:
    print(f"No changes made to {filename}.")
